package ru.skogmark.aggregator.parser.vk.api;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Service;
import ru.skogmark.framework.request.OutgoingRequestService;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Service
public class VkApiService {
    private final OutgoingRequestService outgoingRequestService;

    public VkApiService(@Nonnull OutgoingRequestService outgoingRequestService) {
        this.outgoingRequestService = requireNonNull(outgoingRequestService, "outgoingRequestService");
    }

    public void getUserSubscriptions(Consumer<HttpResponse> onResponseReceivedCallback) {
        HttpGet httpGet = new HttpGet("/method/users.getSubscriptions" +
                "?user_ids=210700286" +
                "&access_token=-1" +
                "&v=5.92");
        outgoingRequestService.execute(HttpHost.create("https://api.vk.com"), httpGet, onResponseReceivedCallback);
    }
}
