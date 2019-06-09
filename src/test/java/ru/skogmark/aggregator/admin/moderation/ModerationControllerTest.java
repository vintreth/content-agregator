package ru.skogmark.aggregator.admin.moderation;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.ApplicationContextAwareTest;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueServiceStub;
import ru.skogmark.aggregator.core.moderation.UnmoderatedPost;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;

public class ModerationControllerTest extends ApplicationContextAwareTest {
    @Autowired
    private PremoderationQueueServiceStub premoderationQueueServiceStub;

    @Autowired
    private HttpClient client;

    @Test
    public void should_render_posts_page() throws IOException {
        ZonedDateTime now = ZonedDateTime.now();
        premoderationQueueServiceStub.setPosts(List.of(
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
                        .build()));
        HttpResponse response = client.execute(HttpHost.create("http://localhost:8185"),
                new HttpGet("/admin/moderation/posts/page/1"));
        String content = IOUtils.toString(response.getEntity().getContent(),
                StandardCharsets.UTF_8);
        IOUtils.write(content, new FileOutputStream("target/test.html"), StandardCharsets.UTF_8);

        Document doc = Jsoup.parse(content);
        Element element = doc.getElementById("posts_table");
        System.out.println(element.getElementsByTag("tr").get(0).getElementsByTag("th").get(0).text());
    }
}