package ru.skogmark.aggregator.parser.vk;

import org.junit.Test;
import ru.skogmark.aggregator.core.PostImage;
import ru.skogmark.aggregator.core.PostImageSize;
import ru.skogmark.aggregator.core.content.Content;
import ru.skogmark.aggregator.core.content.ContentPost;
import ru.skogmark.aggregator.core.content.ParsingContext;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class VkApiParserTest {
    @Test
    public void should_parse_content_when_offset_stored() {
        VkApiResult result = newResult();
        VkApiParser parser = mockParser(result);
        parser.parse(ParsingContext.builder()
                .setSourceId(1)
                .setLimit(10)
                .setOffset(41210L)
                .setOnContentReceivedCallback(content -> {
                    assertNotNull(content);
                    assertEquals(2, content.getPosts().size());
                    assertItem(result.getResponse().get().getItems().get(0), content.getPosts().get(0));
                    assertItem(result.getResponse().get().getItems().get(1), content.getPosts().get(1));
                    assertEquals(41200L, content.getNextOffset().longValue());
                })
                .build());
    }

    @Test
    public void should_parse_content_sync_when_offset_stored() {
        VkApiResult result = newResult();
        VkApiParser parser = mockParser(result);
        Optional<Content> content = parser.parseSync(ParsingContext.builder()
                .setSourceId(1)
                .setLimit(10)
                .setOffset(41210L)
                .build());
        assertTrue(content.isPresent());
        assertEquals(2, content.get().getPosts().size());
        assertItem(result.getResponse().get().getItems().get(0), content.get().getPosts().get(0));
        assertItem(result.getResponse().get().getItems().get(1), content.get().getPosts().get(1));
        assertEquals(41200L, content.get().getNextOffset().longValue());
    }

    @Test
    public void should_parse_content_when_offset_is_null() {
        VkApiResult result = newResult();
        VkApiParser parser = mockParser(result);
        parser.parse(ParsingContext.builder()
                .setSourceId(1)
                .setLimit(10)
                .setOffset(null)
                .setOnContentReceivedCallback(content -> {
                    assertNotNull(content);
                    assertEquals(2, content.getPosts().size());
                    assertItem(result.getResponse().get().getItems().get(0), content.getPosts().get(0));
                    assertItem(result.getResponse().get().getItems().get(1), content.getPosts().get(1));
                    assertEquals(content.getNextOffset().longValue(), 45490L);
                })
                .build());
    }

    @Test
    public void should_parse_content_sync_when_offset_is_null() {
        VkApiResult result = newResult();
        VkApiParser parser = mockParser(result);
        Optional<Content> content = parser.parseSync(ParsingContext.builder()
                .setSourceId(1)
                .setLimit(10)
                .setOffset(null)
                .build());
        assertTrue(content.isPresent());
        assertEquals(2, content.get().getPosts().size());
        assertItem(result.getResponse().get().getItems().get(0), content.get().getPosts().get(0));
        assertItem(result.getResponse().get().getItems().get(1), content.get().getPosts().get(1));
        assertEquals(content.get().getNextOffset().longValue(), 45490L);
    }

    @Test
    public void should_parse_content_when_offset_is_0() {
        VkApiResult result = newResult();
        VkApiParser parser = mockParser(result);
        parser.parse(ParsingContext.builder()
                .setSourceId(1)
                .setLimit(10)
                .setOffset(0L)
                .setOnContentReceivedCallback(content -> {
                    assertNotNull(content);
                    assertEquals(2, content.getPosts().size());
                    assertItem(result.getResponse().get().getItems().get(0), content.getPosts().get(0));
                    assertItem(result.getResponse().get().getItems().get(1), content.getPosts().get(1));
                    assertEquals(content.getNextOffset().longValue(), 0L);
                })
                .build());
    }

    @Test
    public void should_parse_content_sync_when_offset_is_0() {
        VkApiResult result = newResult();
        VkApiParser parser = mockParser(result);
        Optional<Content> content = parser.parseSync(ParsingContext.builder()
                .setSourceId(1)
                .setLimit(10)
                .setOffset(0L)
                .build());
        assertTrue(content.isPresent());
        assertEquals(2, content.get().getPosts().size());
        assertItem(result.getResponse().get().getItems().get(0), content.get().getPosts().get(0));
        assertItem(result.getResponse().get().getItems().get(1), content.get().getPosts().get(1));
        assertEquals(content.get().getNextOffset().longValue(), 0L);
    }

    @Test
    public void should_parse_content_when_offset_is_near_0() {
        VkApiResult result = newResult();
        VkApiParser parser = mockParser(result);
        parser.parse(ParsingContext.builder()
                .setSourceId(1)
                .setLimit(10)
                .setOffset(3L)
                .setOnContentReceivedCallback(content -> {
                    assertNotNull(content);
                    assertEquals(2, content.getPosts().size());
                    assertItem(result.getResponse().get().getItems().get(0), content.getPosts().get(0));
                    assertItem(result.getResponse().get().getItems().get(1), content.getPosts().get(1));
                    assertEquals(content.getNextOffset().longValue(), 0L);
                })
                .build());
    }

    @Test
    public void should_parse_content_sync_when_offset_is_near_0() {
        VkApiResult result = newResult();
        VkApiParser parser = mockParser(result);
        Optional<Content> content = parser.parseSync(ParsingContext.builder()
                .setSourceId(1)
                .setLimit(10)
                .setOffset(3L)
                .build());
        assertTrue(content.isPresent());
        assertEquals(2, content.get().getPosts().size());
        assertItem(result.getResponse().get().getItems().get(0), content.get().getPosts().get(0));
        assertItem(result.getResponse().get().getItems().get(1), content.get().getPosts().get(1));
        assertEquals(content.get().getNextOffset().longValue(), 0L);
    }

    private static void assertItem(Item expectedItem, ContentPost actualItem) {
        assertEquals(expectedItem.getId(), actualItem.getExternalId());
        assertEquals(expectedItem.getText().orElse(null), actualItem.getText().orElse(null));
        assertImage(expectedItem.getAttachments().get(0).getPhoto().get(), actualItem.getImages().get(0));
        assertImage(expectedItem.getAttachments().get(1).getPhoto().get(), actualItem.getImages().get(1));
    }

    private static void assertImage(Photo expectedImage, PostImage actualImage) {
        assertSize(expectedImage.getSizes().get(0), actualImage.getSizes().get(0));
        assertSize(expectedImage.getSizes().get(1), actualImage.getSizes().get(1));
    }

    private static void assertSize(Size expectedSize, PostImageSize actualSize) {
        assertEquals(expectedSize.getUrl(), actualSize.getSrc());
        assertEquals(expectedSize.getWidth(), actualSize.getWidth().orElse(null));
        assertEquals(expectedSize.getHeight(), actualSize.getHeight().orElse(null));
    }

    private VkApiResult newResult() {
        return new VkApiResult(new Response(45500, List.of(
                new Item(24L, "text of item0", List.of(
                        new Attachment("photo", new Photo(List.of(
                                new Size("m", "https://vk.com/size3m", 320, 320),
                                new Size("l", "https://vk.com/size3l", 512, 512)))),
                        new Attachment("photo", new Photo(List.of(
                                new Size("m", "https://vk.com/size0m", 640, 480),
                                new Size("l", "https://vk.com/size0l", 1024, 768)))))),
                new Item(25L, "text of item1", List.of(
                        new Attachment("photo", new Photo(List.of(
                                new Size("m", "https://vk.com/size1m", 640, 480),
                                new Size("l", "https://vk.com/size1l", 1024, 768)))),
                        new Attachment("photo", new Photo(List.of(
                                new Size("m", "https://vk.com/size2m", 320, 240),
                                new Size("l", "https://vk.com/size2l", 512, 256)))))))),
                null);
    }

    private VkApiParser mockParser(VkApiResult result) {
        VkApiClient client = mock(VkApiClient.class);
        doAnswer(invocation -> {
            invocation.getArgumentAt(1, Consumer.class).accept(result);
            return null;
        }).when(client).getWall(any(), any());
        when(client.getWall(any())).thenReturn(result);
        return new VkApiParser(client);
    }
}