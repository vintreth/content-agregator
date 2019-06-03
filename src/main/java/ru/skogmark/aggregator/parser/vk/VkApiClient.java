package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Component;
import ru.skogmark.aggregator.prop.VkParserProperties;
import ru.skogmark.framework.request.OutgoingRequestService;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
public class VkApiClient {
    private final OutgoingRequestService outgoingRequestService;
    private final VkParserProperties vkParserProperties;
    private final ObjectMapper objectMapper;

    public VkApiClient(@Nonnull OutgoingRequestService outgoingRequestService,
                       @Nonnull VkParserProperties vkParserProperties,
                       @Nonnull ObjectMapper objectMapper) {
        this.outgoingRequestService = requireNonNull(outgoingRequestService, "outgoingRequestService");
        this.vkParserProperties = requireNonNull(vkParserProperties, "vkParserProperties");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    public void getWall(@Nonnull GetWallRequest request, @Nonnull Consumer<VkApiResult> onResponseReceivedCallback) {
        requireNonNull(request, "request");
        requireNonNull(onResponseReceivedCallback, "onResponseReceivedCallback");
        outgoingRequestService.execute(
                HttpHost.create(vkParserProperties.getApiUrl()),
                new HttpGet("/method/wall.get?" + new QueryStringBuilder()
                        .put("owner_id", request.getOwner().getId())
                        .put("count", request.getCount())
                        .put("access_token", vkParserProperties.getAccessToken())
                        .put("v", vkParserProperties.getVersion())
                        .put("offset", request.getOffset())
                        .build()),
                new JsonDeserializerCallbackDecorator<>(onResponseReceivedCallback, VkApiResult.class, objectMapper));
    }

    private static class QueryStringBuilder {
        private final Map<String, String> params = new HashMap<>();

        QueryStringBuilder put(String paramName, String paramValue) {
            params.put(paramName, paramValue);
            return this;
        }

        QueryStringBuilder put(String paramName, int paramValue) {
            params.put(paramName, String.valueOf(paramValue));
            return this;
        }

        QueryStringBuilder put(String paramName, long paramValue) {
            params.put(paramName, String.valueOf(paramValue));
            return this;
        }

        String build() {
            return params.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));
        }
    }

    private static class JsonDeserializerCallbackDecorator<T> implements Consumer<HttpResponse> {
        private final Consumer<T> callback;
        private final Class<T> responseClass;
        private final ObjectMapper objectMapper;

        JsonDeserializerCallbackDecorator(@Nonnull Consumer<T> callback,
                                          @Nonnull Class<T> responseClass,
                                          @Nonnull ObjectMapper objectMapper) {
            this.callback = requireNonNull(callback, "callback");
            this.responseClass = requireNonNull(responseClass, "responseClass");
            this.objectMapper = requireNonNull(objectMapper, "objectMapper");
        }

        @Override
        public void accept(HttpResponse httpResponse) {
            try {
                String content = IOUtils.toString(httpResponse.getEntity().getContent(),
                        StandardCharsets.UTF_8);
                T deserializedContent = objectMapper.readValue(content, responseClass);
                callback.accept(deserializedContent);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to read json value from string", e);
            }
        }
    }
}
