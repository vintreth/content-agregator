package ru.skogmark.aggregator.parser.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.ApplicationContextAwareTest;

public class VkApiClientTest extends ApplicationContextAwareTest {
    private static final Logger log = LoggerFactory.getLogger(VkApiClientTest.class);

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