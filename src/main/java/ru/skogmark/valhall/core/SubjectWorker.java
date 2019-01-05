package ru.skogmark.valhall.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skogmark.valhall.core.content.ContentService;
import ru.skogmark.valhall.core.content.Source;
import ru.skogmark.valhall.core.premoderation.PremoderationQueueService;
import ru.skogmark.valhall.core.premoderation.UnmoderatedPost;
import ru.skogmark.valhall.image.Image;
import ru.skogmark.valhall.image.ImageDownloadService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

class SubjectWorker {
    private static final Logger log = LoggerFactory.getLogger(SubjectWorker.class);

    private final ScheduledExecutorService subjectWorkerExecutor;
    private final SubjectService subjectService;
    private final ContentService contentService;
    private final PremoderationQueueService premoderationQueueService;
    private final ImageDownloadService imageDownloadService;

    SubjectWorker(@Nonnull ScheduledExecutorService subjectWorkerExecutor,
                  @Nonnull SubjectService subjectService,
                  @Nonnull ContentService contentService,
                  @Nonnull PremoderationQueueService premoderationQueueService,
                  @Nonnull ImageDownloadService imageDownloadService) {
        this.subjectWorkerExecutor = requireNonNull(subjectWorkerExecutor, "subjectWorkerExecutor");
        this.subjectService = requireNonNull(subjectService, "subjectService");
        this.contentService = requireNonNull(contentService, "contentService");
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
        this.imageDownloadService = requireNonNull(imageDownloadService, "imageDownloadService");
    }

    @PostConstruct
    void start() {
        log.info("Starting worker");
        subjectWorkerExecutor.scheduleWithFixedDelay(this::doWork, 0, 1, TimeUnit.SECONDS);
    }

    private void doWork() {
        subjectService.getSubjects().forEach(this::processSubject);
    }

    private void processSubject(Subject subject) {
        subject.getSources().forEach(this::getContentAndEnqueuePosts);
    }

    private void getContentAndEnqueuePosts(Source source) {
        Timetable timetable = subjectService.getTimetable(source.getId());
        if (!isTimeMatched(ZonedDateTime.now(), timetable)) {
            log.debug("It's not the time to parse content for: source={}", source);
            return;
        }
        contentService.parseContent(source, content -> {
            Optional<Image> image = imageDownloadService.downloadAndSave(content.getImageUri());
            premoderationQueueService.enqueuePost(buildUnmoderatedPost(content.getText(),
                    image.flatMap(Image::getId).orElse(null)));
        });
        // todo remember posted time
        
    }

    static boolean isTimeMatched(ZonedDateTime startingTime, Timetable timetable) {
        return timetable.getTimes().stream()
                .anyMatch(time ->
                        startingTime.getHour() == time.getHour() && startingTime.getMinute() == time.getMinute());
    }

    private static UnmoderatedPost buildUnmoderatedPost(@Nonnull String text, @Nullable Long imageId) {
        requireNonNull(text, "text");
        return UnmoderatedPost.builder()
                .text(text)
                .imageId(imageId)
                .build();
    }
}
