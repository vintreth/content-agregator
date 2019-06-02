package ru.skogmark.aggregator.parser.vk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.ApplicationContextAwareTest;

public class VkApiClientTest extends ApplicationContextAwareTest {
    private static final Logger log = LoggerFactory.getLogger(VkApiClientTest.class);

    @Autowired
    private VkApiClient vkApiClient;

    @Test
    public void should_send_test_request() throws InterruptedException {
        vkApiClient.getWall(GetWallRequest.builder()
                .withOwner(Owner.LEPRA)
                .withCount(3)
                .withOffset(0L)
                .build(), response -> log.info("result={}", response));
        Thread.sleep(5000);
    }
}