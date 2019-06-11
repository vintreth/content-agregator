package ru.skogmark.aggregator.parser.vk;

import org.junit.Test;
import ru.skogmark.aggregator.core.content.ContentPost;
import ru.skogmark.aggregator.core.content.ParsingContext;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class VkApiParserTest {
    @Test
    public void should_parse_content_when_offset_stored() {
        VkApiResult result = createResult();
        VkApiParser parser = createParser(result);
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
    public void should_parse_content_when_offset_is_null() {
        VkApiResult result = createResult();
        VkApiParser parser = createParser(result);
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
    public void should_parse_content_when_offset_is_0() {
        VkApiResult result = createResult();
        VkApiParser parser = createParser(result);
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
    public void should_parse_content_when_offset_is_near_0() {
        VkApiResult result = createResult();
        VkApiParser parser = createParser(result);
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

    private static void assertItem(Item expectedItem, ContentPost actualItem) {
        assertEquals(expectedItem.getId(), actualItem.getExternalId());
        assertEquals(expectedItem.getText().orElse(null), actualItem.getText().orElse(null));
        assertEquals(expectedItem.getAttachments().get(0).getPhoto().get().getSizes().size(),
                actualItem.getImages().size());
        assertEquals(
                expectedItem.getAttachments().get(0).getPhoto().get().getSizes().get(0).getUrl(),
                actualItem.getImages().get(0).getSrc());
        assertEquals(
                expectedItem.getAttachments().get(0).getPhoto().get().getSizes().get(1).getUrl(),
                actualItem.getImages().get(1).getSrc());
    }

    private VkApiResult createResult() {
        return new VkApiResult(new Response(45500, List.of(
                new Item(24L, "text of item0", List.of(
                        new Attachment("photo", new Photo(List.of(
                                new Size("m", "https://vk.com/size0m", 640, 480),
                                new Size("l", "https://vk.com/size0l", 1024, 768)))))),
                new Item(25L, "text of item1", List.of(
                        new Attachment("photo", new Photo(List.of(
                                new Size("m", "https://vk.com/size1m", 640, 480),
                                new Size("l", "https://vk.com/size1l", 1024, 768))))))
        )), null);
    }

    private VkApiParser createParser(VkApiResult result) {
        VkApiClient client = mock(VkApiClient.class);
        doAnswer(invocation -> {
            invocation.getArgumentAt(1, Consumer.class).accept(result);
            return null;
        }).when(client).getWall(any(), any());
        return new VkApiParser(client);
    }
}