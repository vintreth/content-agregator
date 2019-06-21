package ru.skogmark.aggregator.parser.vk;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.skogmark.aggregator.client.JsonHttpClient;
import ru.skogmark.aggregator.client.QueryStringBuilder;
import ru.skogmark.aggregator.prop.VkParserProperties;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

@Component
class VkApiClient {
    private static final Logger log = LoggerFactory.getLogger(VkApiClient.class);

    private final JsonHttpClient httpClient;
    private final VkParserProperties vkParserProperties;

    VkApiClient(@Nonnull JsonHttpClient httpClient,
                @Nonnull VkParserProperties vkParserProperties) {
        this.httpClient = requireNonNull(httpClient, "httpClient");
        this.vkParserProperties = requireNonNull(vkParserProperties, "vkParserProperties");
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
}
