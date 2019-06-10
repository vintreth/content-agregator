package ru.skogmark.aggregator.parser.vk;

import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.ApplicationContextAwareTest;

public class VkApiClientTest extends ApplicationContextAwareTest {
    @Autowired
    private VkApiClient vkApiClient;

    //    @Test
    public void should_send_test_request() throws InterruptedException {
        vkApiClient.getWall(GetWallRequest.builder()
                .setOwner(Owner.LEPRA)
                .setCount(3)
                .setOffset(0L)
                .build(), response -> {
            response.getResponse().get().getItems()
                    .forEach(item -> System.out.println(item.getText()));
        });
        Thread.sleep(5000);
    }
}