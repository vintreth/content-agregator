package ru.skogmark.aggregator.core;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.skogmark.aggregator.core.content.*;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueService;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueServiceImpl;
import ru.skogmark.aggregator.core.moderation.UnmoderatedPost;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WorkerTest {
    @Test
    public void should_return_true_if_time_matched() {
        ZonedDateTime startingTime = ZonedDateTime.of(2019, 1, 1, 19, 0, 45, 0, ZoneId.systemDefault());
        Timetable timetable0 = Timetable.of(Set.of(LocalTime.of(19, 0),
                LocalTime.of(7, 0)));
        Timetable timetable1 = Timetable.of(Set.of(LocalTime.of(18, 0),
                LocalTime.of(19, 0)));
        assertTrue(Worker.isTimeMatched(startingTime, timetable0));
        assertTrue(Worker.isTimeMatched(startingTime, timetable1));
    }

    @Test
    public void should_return_false_if_time_not_matched() {
        ZonedDateTime startingTime0 = ZonedDateTime.of(2019, 1, 1, 19, 0, 45, 0, ZoneId.systemDefault());
        ZonedDateTime startingTime1 = ZonedDateTime.of(2019, 1, 1, 19, 1, 12, 0, ZoneId.systemDefault());
        Timetable timetable0 = Timetable.of(Set.of(LocalTime.of(1, 0),
                LocalTime.of(13, 0)));
        Timetable timetable1 = Timetable.of(Set.of(LocalTime.of(19, 0),
                LocalTime.of(0, 0)));
        assertFalse(Worker.isTimeMatched(startingTime0, timetable0));
        assertFalse(Worker.isTimeMatched(startingTime1, timetable1));
    }

    @Test
    public void should_parse_content_and_enqueue_posts() {
        Content content = new Content(List.of(
                ContentPost.builder()
                        .setExternalId(53533135L)
                        .setTitle("title0")
                        .setText("text0")
                        .setImages(List.of(
                                PostImage.builder()
                                        .setSrc("img0")
                                        .setWidth(640)
                                        .setHeight(480)
                                        .build(),
                                PostImage.builder()
                                        .setSrc("img1")
                                        .setWidth(1024)
                                        .setHeight(768)
                                        .build()))
                        .build(),
                ContentPost.builder()
                        .setExternalId(53533136L)
                        .setTitle("title1")
                        .setText("text1")
                        .setImages(List.of(
                                PostImage.builder()
                                        .setSrc("img2")
                                        .setWidth(640)
                                        .setHeight(480)
                                        .build(),
                                PostImage.builder()
                                        .setSrc("img3")
                                        .setWidth(1024)
                                        .setHeight(768)
                                        .build()))
                        .build()), 1002L);
        SourceContext sourceContext = SourceContext.builder()
                .setSourceId(1)
                .setTimetable(Timetable.of(Set.of(LocalTime.of(12, 0), LocalTime.of(16, 0))))
                .setParser(createParser(content))
                .setParserLimit(5)
                .build();
        ArgumentCaptor<List> postsCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<Integer> sourceIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> offsetCaptor = ArgumentCaptor.forClass(Long.class);
        Worker worker = createWorker(postsCaptor, sourceIdCaptor, offsetCaptor);
        ZonedDateTime parsingTime = ZonedDateTime.of(2019, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault());

        worker.parseContentAndEnqueuePosts(1, sourceContext, parsingTime);

        List<UnmoderatedPost> unmoderatedPosts = postsCaptor.getValue();
        assertEquals(2, unmoderatedPosts.size());
        assertPost(content.getPosts().get(0), unmoderatedPosts.get(0));
        assertPost(content.getPosts().get(1), unmoderatedPosts.get(1));

        assertEquals(1, sourceIdCaptor.getValue().intValue());
        assertEquals(1002, offsetCaptor.getValue().longValue());
    }

    private static void assertPost(ContentPost expectedPost, UnmoderatedPost actualPost) {
        assertFalse(actualPost.getId().isPresent());
        assertEquals(1, actualPost.getChannelId().intValue());
        assertEquals(expectedPost.getTitle(), actualPost.getTitle());
        assertEquals(expectedPost.getText(), actualPost.getText());
        assertEquals(expectedPost.getImages().size(), actualPost.getImages().size());
        assertTrue(expectedPost.getImages().containsAll(actualPost.getImages()));
        assertFalse(actualPost.getCreatedDt().isPresent());
        assertFalse(actualPost.getChangedDt().isPresent());
    }

    private static Parser createParser(Content content) {
        Parser parser = mock(Parser.class);
        doAnswer(invocation -> {
            invocation.getArgumentAt(0, ParsingContext.class).getOnContentReceivedCallback().accept(content);
            return null;
        }).when(parser).parse(any());
        return parser;
    }

    private static Worker createWorker(ArgumentCaptor<List> postsCaptor,
                                       ArgumentCaptor<Integer> sourceIdCaptor,
                                       ArgumentCaptor<Long> offsetCaptor) {
        SourceService sourceService = mock(SourceService.class);
        when(sourceService.getOffset(1)).thenReturn(Optional.of(1000L));
        doAnswer(invocation -> null).when(sourceService).updateOffset(sourceIdCaptor.capture(), offsetCaptor.capture());

        PremoderationQueueService premoderationQueueService = mock(PremoderationQueueServiceImpl.class);
        doAnswer(invocation -> null).when(premoderationQueueService).enqueuePosts(postsCaptor.capture());

        return new Worker(
                mock(ScheduledExecutorService.class),
                Collections.emptyList(),
                sourceService,
                premoderationQueueService,
                new ParsingTimeStorage());
    }
}