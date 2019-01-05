package ru.skogmark.valhall.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skogmark.valhall.core.config.SubjectsConfiguration;
import ru.skogmark.valhall.core.content.ContentService;
import ru.skogmark.valhall.core.premoderation.PremoderationQueueService;
import ru.skogmark.valhall.core.premoderation.UnmoderatedPost;
import ru.skogmark.valhall.image.Image;
import ru.skogmark.valhall.image.ImageDownloadService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

class SubjectWorker {
    private static final Logger log = LoggerFactory.getLogger(SubjectWorker.class);

    private final ScheduledExecutorService subjectWorkerExecutor;
    private final SubjectsConfiguration subjectsConfiguration;
    private final ContentService contentService;
    private final PremoderationQueueService premoderationQueueService;
    private final ImageDownloadService imageDownloadService;
    private final ParsingTimeStorage parsingTimeStorage = new ParsingTimeStorage();

    SubjectWorker(@Nonnull ScheduledExecutorService subjectWorkerExecutor,
                  @Nonnull SubjectsConfiguration subjectsConfiguration,
                  @Nonnull ContentService contentService,
                  @Nonnull PremoderationQueueService premoderationQueueService,
                  @Nonnull ImageDownloadService imageDownloadService) {
        this.subjectWorkerExecutor = requireNonNull(subjectWorkerExecutor, "subjectWorkerExecutor");
        this.subjectsConfiguration = requireNonNull(subjectsConfiguration, "subjectsConfiguration");
        this.contentService = requireNonNull(contentService, "contentService");
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
        this.imageDownloadService = requireNonNull(imageDownloadService, "imageDownloadService");
    }

    @PostConstruct
    void start() {
        log.info("Starting worker");
        subjectWorkerExecutor.scheduleWithFixedDelay(() -> getSubjects()
                        .forEach(subject -> subject.getSources()
                                .forEach(source -> parseContentAndEnqueuePosts(subject, source))),
                0, 1, TimeUnit.SECONDS);
    }

    private List<Subject> getSubjects() {
        return subjectsConfiguration.getSubjects().getItems().stream()
                .map(subject -> Subject.builder()
                        .setId(subject.getId())
                        .setName(subject.getName())
                        .setSources(subject.getSources().getItems().stream()
                                .map(source -> Source.builder()
                                        .setId(source.getId())
                                        .setParsingLimit(source.getParsingLimit())
                                        .setTimetable(Timetable.of(new HashSet<>(source.getTimetable().getTimes())))
                                        .build())
                                .collect(Collectors.toSet()))
                        .build())
                .collect(Collectors.toList());
    }

    private void parseContentAndEnqueuePosts(Subject subject, Source source) {
        ZonedDateTime parsingTime = ZonedDateTime.now();
        if (parsingTimeStorage.minuteExists(subject.getId(), source.getId(), parsingTime)) {
            log.debug("Source has been parsed already at this minute: source={}", source);
            return;
        }
        if (!isTimeMatched(parsingTime, getTimetable(subject, source))) {
            log.debug("It's not the time to parse content for: source={}", source);
            return;
        }
        log.info("Parsing content for: subject={}, source={}", subject, source);
        contentService.parseContent(source.getId(), content -> {
            Optional<Image> image = imageDownloadService.downloadAndSave(content.getImageUri());
            premoderationQueueService.enqueuePost(buildUnmoderatedPost(content.getText(),
                    image.flatMap(Image::getId).orElse(null)));
        });
        parsingTimeStorage.put(subject.getId(), source.getId(), parsingTime);
    }

    private static Timetable getTimetable(Subject subject, Source source) {
        return subject.getSources().stream()
                .filter(s -> s.getId() == source.getId())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("No timetable exists: subject=" + subject + ", source=" + source))
                .getTimetable();
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
