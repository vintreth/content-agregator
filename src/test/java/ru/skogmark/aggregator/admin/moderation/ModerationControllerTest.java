package ru.skogmark.aggregator.admin.moderation;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.JspEndpointTest;
import ru.skogmark.aggregator.channel.Channel;
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
                        .setText("Text of the first post")
                        .setImages(List.of("img1", "img2"))
                        .setCreatedDt(now)
                        .build(),
                UnmoderatedPost.builder()
                        .setId(2L)
                        .setChannelId(1)
                        .setText("Text of the second post")
                        .setImages(List.of("img3", "img4"))
                        .setCreatedDt(now)
                        .build(),
                UnmoderatedPost.builder()
                        .setId(3L)
                        .setChannelId(1)
                        .setText("Text of the third post")
                        .setImages(List.of("img5", "img6"))
                        .setCreatedDt(now)
                        .build());
        premoderationQueueServiceStub.setPosts(posts);

        Document doc = callJspEndpoint("/admin/moderation/posts/page/1");

        Element postsTable = doc.getElementById("posts-table");
        assertNotNull(postsTable);

        Elements rows = postsTable.getElementsByTag("tr");
        assertEquals(4, rows.size());

        Elements header = rows.get(0).getElementsByTag("th");
        assertEquals(5, header.size());
        assertEquals("Id", header.get(0).text());
        assertEquals("Channel", header.get(1).text());
        assertEquals("Text", header.get(2).text());
        assertEquals("Images", header.get(3).text());
        assertEquals("Creation time", header.get(4).text());

        assertRow(posts.get(0), rows.get(1));
        assertRow(posts.get(1), rows.get(2));
        assertRow(posts.get(2), rows.get(3));
    }

    private static void assertRow(UnmoderatedPost expectedData, Element actualData) {
        Elements cells = actualData.getElementsByTag("td");
        assertEquals(5, cells.size());
        assertEquals(expectedData.getId().map(String::valueOf).orElse(null), cells.get(0).text());
        assertEquals(Channel.MEMES.getName(), cells.get(1).text());
        assertEquals(expectedData.getText(), cells.get(2).text());

        Elements imgs = cells.get(3).getElementsByTag("img");
        assertEquals(expectedData.getImages().size(), imgs.size());
        assertEquals(expectedData.getImages().get(0), imgs.get(0).attr("src"));
        assertEquals(expectedData.getImages().get(1), imgs.get(1).attr("src"));

        assertEquals(ModerationController.viewTimeFormatter.format(expectedData.getCreatedDt().get()),
                cells.get(4).text());
    }
}