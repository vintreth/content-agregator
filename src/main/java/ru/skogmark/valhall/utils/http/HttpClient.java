package ru.skogmark.valhall.utils.http;

import org.springframework.stereotype.Component;
import ru.skogmark.common.http.*;

import java.net.URI;

@Component
public class HttpClient {
    private final HttpRequest httpClient = new SerializerAwareHttpRequest(new JacksonObjectMapperSerializerAdapter());

    public String doGet(String url) {
        return doGet(url, String.class);
    }

    public <T> T doGet(String url, Class<T> responseClass) {
        HttpRequestHeader header = new HttpRequestHeader();
        header.setHttpMethod(HttpMethod.GET);
        header.setUrl(url);
        return httpClient.makeRequest(header, null, responseClass);
    }
}
