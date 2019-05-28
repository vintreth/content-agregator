package ru.skogmark.aggregator.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.skogmark.aggregator.core.content.ContentService;
import ru.skogmark.aggregator.core.premoderation.PremoderationQueueService;
import ru.skogmark.aggregator.core.premoderation.UnmoderatedPost;
import ru.skogmark.aggregator.image.Image;
import ru.skogmark.aggregator.image.ImageDownloadService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

@Component
class Worker implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    private final ScheduledExecutorService subjectWorkerExecutor;
    private final List<ChannelContext> channelContexts;
    private final ContentService contentService;
    private final PremoderationQueueService premoderationQueueService;
    private final ImageDownloadService imageDownloadService;
    private final ParsingTimeStorage parsingTimeStorage = new ParsingTimeStorage();

    Worker(@Nonnull ScheduledExecutorService subjectWorkerExecutor,
           @Nonnull List<ChannelContext> channelContexts,
           @Nonnull ContentService contentService,
           @Nonnull PremoderationQueueService premoderationQueueService,
           @Nonnull ImageDownloadService imageDownloadService) {
        this.subjectWorkerExecutor = requireNonNull(subjectWorkerExecutor, "subjectWorkerExecutor");
        this.channelContexts = requireNonNull(channelContexts, "channelContexts");
        this.contentService = requireNonNull(contentService, "contentService");
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
        this.imageDownloadService = requireNonNull(imageDownloadService, "imageDownloadService");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Starting worker");
        subjectWorkerExecutor.scheduleWithFixedDelay(() -> channelContexts
                        .forEach(channelContext -> channelContext.getSourceContexts()
                                .forEach(sourceContext -> parseContentAndEnqueuePosts(channelContext, sourceContext))),
                0, 1, TimeUnit.SECONDS);
    }

    private void parseContentAndEnqueuePosts(ChannelContext channelContext, SourceContext sourceContext) {
        ZonedDateTime parsingTime = ZonedDateTime.now();
        if (parsingTimeStorage.minuteExists(channelContext.getId(), sourceContext.getId(), parsingTime)) {
            log.debug("SourceContext has been parsed already at this minute: sourceContext={}", sourceContext);
            return;
        }
        if (!isTimeMatched(parsingTime, getTimetable(channelContext, sourceContext))) {
            log.debug("It's not the time to parse content for: sourceContext={}", sourceContext);
            return;
        }
        log.info("Parsing content for: channelContext={}, sourceContext={}", channelContext, sourceContext);
        contentService.parseContent(sourceContext, content -> {
            Optional<Image> image = imageDownloadService.downloadAndSave(content.getImageUri());
            premoderationQueueService.enqueuePost(buildUnmoderatedPost(content.getText(),
                    image.flatMap(Image::getId).orElse(null)));
        });
        parsingTimeStorage.put(channelContext.getId(), sourceContext.getId(), parsingTime);
    }

    private static Timetable getTimetable(ChannelContext channelContext, SourceContext sourceContext) {
        return channelContext.getSourceContexts().stream()
                .filter(s -> s.getId() == sourceContext.getId())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("No timetable exists: channelContext=" + channelContext +
                                ", sourceContext=" + sourceContext))
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
