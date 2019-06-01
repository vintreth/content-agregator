package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Service;
import ru.skogmark.aggregator.prop.VkParserProperties;
import ru.skogmark.framework.request.OutgoingRequestService;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Service
public class VkApiService {
    private final OutgoingRequestService outgoingRequestService;
    private final VkParserProperties vkParserProperties;
    private final ObjectMapper objectMapper;

    public VkApiService(@Nonnull OutgoingRequestService outgoingRequestService,
                        @Nonnull VkParserProperties vkParserProperties,
                        @Nonnull ObjectMapper objectMapper) {
        this.outgoingRequestService = requireNonNull(outgoingRequestService, "outgoingRequestService");
        this.vkParserProperties = requireNonNull(vkParserProperties, "vkParserProperties");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    public void getWallMessages(Consumer<VkApiResult> onResponseReceivedCallback) {
        HttpGet httpGet = new HttpGet("/method/wall.get" +
                "?owner_id=-30022666" +
//                "&offset=90200" + // todo store and retrieve offset
                "&count=3" +
                "&access_token=" + vkParserProperties.getAccessToken() +
                "&v=" + vkParserProperties.getVersion());
        outgoingRequestService.execute(
                HttpHost.create(vkParserProperties.getApiUrl()), httpGet, httpResponse -> {
                    try {
                        String content = IOUtils.toString(httpResponse.getEntity().getContent(),
                                StandardCharsets.UTF_8);
                        VkApiResult result = objectMapper.readValue(content, VkApiResult.class);
                        onResponseReceivedCallback.accept(result);
                    } catch (IOException e) {
                        throw new IllegalStateException("Unable to read json value from string", e);
                    }
                });
    }
}
