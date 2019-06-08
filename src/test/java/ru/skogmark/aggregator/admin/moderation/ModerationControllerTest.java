package ru.skogmark.aggregator.admin.moderation;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import ru.skogmark.aggregator.ApplicationContextAwareTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ModerationControllerTest extends ApplicationContextAwareTest {
    @Test
    public void name() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(HttpHost.create("http://localhost:8185"), new HttpGet("/admin/moderation/posts/page/1"));
        String content = IOUtils.toString(response.getEntity().getContent(),
                StandardCharsets.UTF_8);
        System.out.println(content);
    }
}