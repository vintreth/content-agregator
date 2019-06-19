package ru.skogmark.aggregator.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;

@Component
public class JsonHttpClient {
    private static final Logger log = LoggerFactory.getLogger(JsonHttpClient.class);

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public JsonHttpClient(@Nonnull HttpClient httpClient,
                          @Nonnull ObjectMapper objectMapper) {
        this.httpClient = requireNonNull(httpClient, "httpClient");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    @Nonnull
    public <T> T execute(HttpHost target, HttpRequest request, Class<T> responseClass) {
        try {
            HttpResponse httpResponse = execute(target, request);
            String content = IOUtils.toString(httpResponse.getEntity().getContent(),
                    StandardCharsets.UTF_8);
            log.debug("Deserializing json to object: json={}", content);
            return objectMapper.readValue(content, responseClass);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read json value from string", e);
        }
    }

    private HttpResponse execute(HttpHost target, HttpRequest request) {
        try {
            return httpClient.execute(target, request);
        } catch (IOException e) {
            throw new IllegalStateException("Exception caught during http request execution", e);
        }
    }
}
