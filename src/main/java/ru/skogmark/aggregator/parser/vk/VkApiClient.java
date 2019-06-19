package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.skogmark.aggregator.client.JsonHttpClient;
import ru.skogmark.aggregator.client.QueryStringBuilder;
import ru.skogmark.aggregator.prop.VkParserProperties;
import ru.skogmark.framework.request.OutgoingRequestService;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Component
class VkApiClient {
    private static final Logger log = LoggerFactory.getLogger(VkApiClient.class);

    private final JsonHttpClient httpClient;
    private final OutgoingRequestService outgoingRequestService;
    private final VkParserProperties vkParserProperties;
    private final ObjectMapper objectMapper;

    VkApiClient(@Nonnull JsonHttpClient httpClient,
                @Nonnull OutgoingRequestService outgoingRequestService,
                @Nonnull VkParserProperties vkParserProperties,
                @Nonnull ObjectMapper objectMapper) {
        this.httpClient = requireNonNull(httpClient, "httpClient");
        this.outgoingRequestService = requireNonNull(outgoingRequestService, "outgoingRequestService");
        this.vkParserProperties = requireNonNull(vkParserProperties, "vkParserProperties");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    void getWall(@Nonnull GetWallRequest request, @Nonnull Consumer<VkApiResult> onResultReceivedCallback) {
        requireNonNull(request, "request");
        requireNonNull(onResultReceivedCallback, "onResultReceivedCallback");
        outgoingRequestService.execute(
                HttpHost.create(vkParserProperties.getApiUrl()),
                new HttpGet(QueryStringBuilder.of("/method/wall.get")
                        .addParam("owner_id", request.getOwner().getId())
                        .addParam("count", request.getCount())
                        .addParam("access_token", vkParserProperties.getAccessToken())
                        .addParam("v", vkParserProperties.getVersion())
                        .addParam("offset", request.getOffset().orElse(null))
                        .build()),
                new JsonDeserializerCallbackDecorator<>(onResultReceivedCallback, VkApiResult.class, objectMapper));
    }

    @Nonnull
    VkApiResult getWall(@Nonnull GetWallRequest request) {
        requireNonNull(request, "request");
        log.info("getWall(): request={}", request);
        return httpClient.execute(HttpHost.create(
                vkParserProperties.getApiUrl()),
                new HttpGet(QueryStringBuilder.of("/method/wall.get")
                        .addParam("owner_id", request.getOwner().getId())
                        .addParam("count", request.getCount())
                        .addParam("access_token", vkParserProperties.getAccessToken())
                        .addParam("v", vkParserProperties.getVersion())
                        .addParam("offset", request.getOffset().orElse(null))
                        .build()),
                VkApiResult.class);
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
