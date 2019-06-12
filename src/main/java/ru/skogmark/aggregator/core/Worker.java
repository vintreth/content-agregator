package ru.skogmark.aggregator.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.skogmark.aggregator.core.content.ContentPost;
import ru.skogmark.aggregator.core.content.ParsingContext;
import ru.skogmark.aggregator.core.content.SourceService;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueService;
import ru.skogmark.aggregator.core.moderation.UnmoderatedPost;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
class Worker implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    private final ScheduledExecutorService workerExecutor;
    private final List<ChannelContext> channelContexts;
    private final SourceService sourceService;
    private final PremoderationQueueService premoderationQueueService;
    private final ParsingTimeStorage parsingTimeStorage;

    Worker(@Nonnull ScheduledExecutorService workerExecutor,
           @Nonnull List<ChannelContext> channelContexts,
           @Nonnull SourceService sourceService,
           @Nonnull PremoderationQueueService premoderationQueueService,
           @Nonnull ParsingTimeStorage parsingTimeStorage) {
        this.workerExecutor = requireNonNull(workerExecutor, "workerExecutor");
        this.channelContexts = requireNonNull(channelContexts, "channelContexts");
        this.sourceService = requireNonNull(sourceService, "sourceService");
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
        this.parsingTimeStorage = requireNonNull(parsingTimeStorage, "parsingTimeStorage");
    }

    @Override
    public void afterPropertiesSet() {
        log.info("Starting worker");
        workerExecutor.scheduleWithFixedDelay(() -> channelContexts
                        .forEach(channelContext -> channelContext.getSourceContexts()
                                .forEach(sourceContext -> parseContentAndEnqueuePosts(
                                        channelContext.getChannelId(), sourceContext, ZonedDateTime.now()))),
                0, 1, TimeUnit.SECONDS);
    }

    // todo tests
    void parseContentAndEnqueuePosts(int channelId,
                                     SourceContext sourceContext,
                                     ZonedDateTime parsingTime) {
        if (parsingTimeStorage.minuteExists(channelId, sourceContext.getSourceId(), parsingTime)) {
            log.debug("SourceContext has been parsed already at this minute: sourceContext={}", sourceContext);
            return;
        }
        if (!isTimeMatched(parsingTime, sourceContext.getTimetable())) {
            log.debug("It's not the time to parse content for: sourceContext={}", sourceContext);
            return;
        }
        log.info("Parsing content for: channelId={}, sourceContext={}", channelId, sourceContext);
        // todo enqueue async task for parser?
        parseContent(sourceContext, channelId);
        parsingTimeStorage.put(channelId, sourceContext.getSourceId(), parsingTime);
    }

    static boolean isTimeMatched(ZonedDateTime startingTime, Timetable timetable) {
        return timetable.getTimes().stream()
                .anyMatch(time ->
                        startingTime.getHour() == time.getHour() && startingTime.getMinute() == time.getMinute());
    }

    private void parseContent(SourceContext sourceContext, int channelId) {
        log.info("parseContent(): sourceId={}", sourceContext.getSourceId());
        Long offset = sourceService.getOffset(sourceContext.getSourceId()).orElse(null);
        sourceContext.getParser().parse(ParsingContext.builder()
                .setSourceId(sourceContext.getSourceId())
                .setLimit(sourceContext.getParserLimit())
                .setOffset(offset)
                .setOnContentReceivedCallback(content -> {
                    log.info("Content obtained: sourceId={}, content={}", sourceContext.getSourceId(), content);
                    premoderationQueueService.enqueuePosts(content.getPosts().stream()
                            .map(post -> toUnmoderatedPost(post, channelId))
                            .collect(Collectors.toList()));
                    if (!content.getNextOffset().equals(offset)) {
                        sourceService.updateOffset(sourceContext.getSourceId(), content.getNextOffset());
                    }
                })
                .build());
    }

    private static UnmoderatedPost toUnmoderatedPost(@Nonnull ContentPost contentPost, int channelId) {
        requireNonNull(contentPost, "contentPost");
        return UnmoderatedPost.builder()
                .setChannelId(channelId)
                .setTitle(contentPost.getTitle().orElse(null))
                .setText(contentPost.getText().orElse(null))
                .setImages(contentPost.getImages())
                .build();
    }
}
