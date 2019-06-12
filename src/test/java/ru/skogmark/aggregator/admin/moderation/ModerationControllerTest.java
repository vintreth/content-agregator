package ru.skogmark.aggregator.admin.moderation;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.JspEndpointTest;
import ru.skogmark.aggregator.channel.Channel;
import ru.skogmark.aggregator.core.PostImage;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueServiceStub;
import ru.skogmark.aggregator.core.moderation.UnmoderatedPost;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModerationControllerTest extends JspEndpointTest {
    @Autowired
    private PremoderationQueueServiceStub premoderationQueueServiceStub;

    @Test
    public void should_render_posts_page() throws IOException {
        ZonedDateTime now = ZonedDateTime.now();
        List<UnmoderatedPost> posts = List.of(
                UnmoderatedPost.builder()
                        .setId(1L)
                        .setChannelId(1)
                        .setTitle("Title #1")
                        .setText("Text of the first post")
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
                        .setCreatedDt(now)
                        .build(),
                UnmoderatedPost.builder()
                        .setId(2L)
                        .setChannelId(1)
                        .setTitle("Title #2")
                        .setText("Text of the second post")
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
                        .setCreatedDt(now)
                        .build(),
                UnmoderatedPost.builder()
                        .setId(3L)
                        .setChannelId(1)
                        .setTitle("Title #3")
                        .setText("Text of the third post")
                        .setImages(List.of(
                                PostImage.builder()
                                        .setSrc("img4")
                                        .setWidth(640)
                                        .setHeight(480)
                                        .build(),
                                PostImage.builder()
                                        .setSrc("img5")
                                        .setWidth(1024)
                                        .setHeight(768)
                                        .build()))
                        .setCreatedDt(now)
                        .build());
        premoderationQueueServiceStub.setPosts(posts);

        Document doc = callJspEndpoint("/admin/moderation/posts/page/1/");

        Element postsTable = doc.getElementById("posts-table");
        assertNotNull(postsTable);

        Elements rows = postsTable.getElementsByTag("tr");
        assertEquals(4, rows.size());

        Elements header = rows.get(0).getElementsByTag("th");
        assertEquals(6, header.size());
        assertEquals("#", header.get(0).text());
        assertEquals("Канал", header.get(1).text());
        assertEquals("Заголовок", header.get(2).text());
        assertEquals("Текст", header.get(3).text());
        assertEquals("Изображения", header.get(4).text());
        assertEquals("Время создания", header.get(5).text());

        assertRow(posts.get(0), rows.get(1));
        assertRow(posts.get(1), rows.get(2));
        assertRow(posts.get(2), rows.get(3));
    }

    private static void assertRow(UnmoderatedPost expectedData, Element actualData) {
        Elements cells = actualData.getElementsByTag("td");
        assertEquals(6, cells.size());
        assertEquals(expectedData.getId().map(String::valueOf).orElse(null), cells.get(0).text());
        assertEquals(Channel.MEMES.getName(), cells.get(1).text());
        assertEquals(expectedData.getTitle().orElse(null), cells.get(2).text());
        assertEquals(expectedData.getText().orElse(null), cells.get(3).text());

        Elements span = cells.get(4).getElementsByTag("span");
        assertEquals(1, span.size());
        if (expectedData.getImages().isEmpty()) {
            assertEquals("Нет", span.get(0).text());
        } else {
            assertEquals(String.valueOf(expectedData.getImages().size()), span.get(0).text());
        }

        assertEquals(ModerationController.viewTimeFormatter.format(expectedData.getCreatedDt().get()),
                cells.get(5).text());
    }
}