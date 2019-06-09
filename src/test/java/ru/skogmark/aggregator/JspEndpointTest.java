package ru.skogmark.aggregator;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JspEndpointTest extends ApplicationContextAwareTest {
    @Autowired
    private HttpClient client;

    public Document callJspEndpoint(String endpointUrl) throws IOException {
        String content = callEndpointInternal(endpointUrl);
        writeHtmlFile(content, endpointUrl);
        return Jsoup.parse(content);
    }

    private String callEndpointInternal(String endpointUrl) throws IOException {
        HttpResponse response = client.execute(HttpHost.create("http://localhost:8185"), new HttpGet(endpointUrl));
        return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
    }

    private void writeHtmlFile(String content, String endpointUrl) throws IOException {
        String outputHtmlFileName = endpointUrl.replace('/', '_') + ".html";
        IOUtils.write(content, new FileOutputStream("target/" + outputHtmlFileName), StandardCharsets.UTF_8);
    }
}
