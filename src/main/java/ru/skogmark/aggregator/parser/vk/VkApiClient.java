package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.skogmark.aggregator.prop.VkParserProperties;
import ru.skogmark.framework.request.OutgoingRequestService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
class VkApiClient {
    private final OutgoingRequestService outgoingRequestService;
    private final VkParserProperties vkParserProperties;
    private final ObjectMapper objectMapper;

    VkApiClient(@Nonnull OutgoingRequestService outgoingRequestService,
                @Nonnull VkParserProperties vkParserProperties,
                @Nonnull ObjectMapper objectMapper) {
        this.outgoingRequestService = requireNonNull(outgoingRequestService, "outgoingRequestService");
        this.vkParserProperties = requireNonNull(vkParserProperties, "vkParserProperties");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    void getWall(@Nonnull GetWallRequest request, @Nonnull Consumer<VkApiResult> onResultReceivedCallback) {
        requireNonNull(request, "request");
        requireNonNull(onResultReceivedCallback, "onResultReceivedCallback");
        outgoingRequestService.execute(
                HttpHost.create(vkParserProperties.getApiUrl()),
                new HttpGet("/method/wall.get?" + new QueryStringBuilder()
                        .put("owner_id", request.getOwner().getId())
                        .put("count", request.getCount())
                        .put("access_token", vkParserProperties.getAccessToken())
                        .put("v", vkParserProperties.getVersion())
                        .put("offset", request.getOffset().orElse(null))
                        .build()),
                new JsonDeserializerCallbackDecorator<>(onResultReceivedCallback, VkApiResult.class, objectMapper));
    }

    private static class QueryStringBuilder {
        private final Map<String, String> params = new HashMap<>();

        QueryStringBuilder put(@Nonnull String paramName, int paramValue) {
            put(paramName, String.valueOf(paramValue));
            return this;
        }

        QueryStringBuilder put(@Nonnull String paramName, long paramValue) {
            put(paramName, String.valueOf(paramValue));
            return this;
        }

        QueryStringBuilder put(@Nonnull String paramName, @Nullable Object paramValue) {
            put(paramName, paramValue == null ? null : String.valueOf(paramValue));
            return this;
        }

        QueryStringBuilder put(@Nonnull String paramName, @Nullable String paramValue) {
            requireNonNull(paramName, "paramName");
            if (paramValue != null && !paramValue.isEmpty()) {
                params.put(paramName, paramValue);
            }
            return this;
        }

        String build() {
            return params.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));
        }
    }

    private static class JsonDeserializerCallbackDecorator<T> implements Consumer<HttpResponse> {
        private static final Logger log = LoggerFactory.getLogger(JsonDeserializerCallbackDecorator.class);

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
                log.debug("Deserializing json to object: json={}", content);
                T deserializedContent = objectMapper.readValue(content, responseClass);
                callback.accept(deserializedContent);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to read json value from string", e);
            }
        }
    }
}
