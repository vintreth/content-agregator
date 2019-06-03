package ru.skogmark.aggregator.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.skogmark.aggregator.core.content.Content;
import ru.skogmark.aggregator.core.content.SourceDao;
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
    private final SourceDao sourceDao;
    private final PremoderationQueueService premoderationQueueService;
    private final ParsingTimeStorage parsingTimeStorage;

    Worker(@Nonnull ScheduledExecutorService workerExecutor,
           @Nonnull List<ChannelContext> channelContexts,
           @Nonnull SourceDao sourceDao,
           @Nonnull PremoderationQueueService premoderationQueueService,
           @Nonnull ParsingTimeStorage parsingTimeStorage) {
        this.workerExecutor = requireNonNull(workerExecutor, "workerExecutor");
        this.channelContexts = requireNonNull(channelContexts, "channelContexts");
        this.sourceDao = requireNonNull(sourceDao, "sourceDao");
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
        this.parsingTimeStorage = requireNonNull(parsingTimeStorage, "parsingTimeStorage");
    }

    @Override
    public void afterPropertiesSet() {
        log.info("Starting worker");
        workerExecutor.scheduleWithFixedDelay(() -> channelContexts
                        .forEach(channelContext -> channelContext.getSourceContexts()
                                .forEach(sourceContext -> parseContentAndEnqueuePosts(channelContext, sourceContext))),
                0, 1, TimeUnit.SECONDS);
    }

    // todo tests
    void parseContentAndEnqueuePosts(ChannelContext channelContext, SourceContext sourceContext) {
        ZonedDateTime parsingTime = ZonedDateTime.now();
        if (parsingTimeStorage.minuteExists(channelContext.getChannelId(), sourceContext.getSourceId(), parsingTime)) {
            log.debug("SourceContext has been parsed already at this minute: sourceContext={}", sourceContext);
            return;
        }
        if (!isTimeMatched(parsingTime, getTimetable(channelContext, sourceContext))) {
            log.debug("It's not the time to parse content for: sourceContext={}", sourceContext);
            return;
        }
        log.info("Parsing content for: channelContext={}, sourceContext={}", channelContext, sourceContext);
        // todo enqueue async task for parser?
        parseContent(sourceContext, channelContext.getChannelId());
        parsingTimeStorage.put(channelContext.getChannelId(), sourceContext.getSourceId(), parsingTime);
    }

    private static Timetable getTimetable(ChannelContext channelContext, SourceContext sourceContext) {
        return channelContext.getSourceContexts().stream()
                .filter(s -> s.getSourceId() == sourceContext.getSourceId())
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

    private void parseContent(SourceContext sourceContext, int channelId) {
        log.info("parseContent(): sourceId={}", sourceContext.getSourceId());
        long offset = sourceDao.getOffset(sourceContext.getSourceId()).orElse(0L);
        sourceContext.getParser().parse(
                sourceContext.getSourceId(),
                sourceContext.getParserLimit(),
                offset,
                contents -> {
                    log.info("Content obtained: sourceId={}, contents={}", sourceContext.getSourceId(), contents);
                    premoderationQueueService.enqueuePosts(contents.stream()
                            .map(content -> toUnmoderatedPost(content, channelId))
                            .collect(Collectors.toList()));
                    // todo change offset and store
                });
    }

    private static UnmoderatedPost toUnmoderatedPost(@Nonnull Content content, int channelId) {
        requireNonNull(content, "content");
        return UnmoderatedPost.builder()
                .setChannelId(channelId)
                .setText(content.getText())
                .setImages(content.getImages())
                .build();
    }
}
