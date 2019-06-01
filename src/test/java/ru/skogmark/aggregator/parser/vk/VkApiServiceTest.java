package ru.skogmark.aggregator.parser.vk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skogmark.aggregator.ApplicationContextAwareTest;

public class VkApiServiceTest extends ApplicationContextAwareTest {
    private static final Logger log = LoggerFactory.getLogger(VkApiServiceTest.class);

    @Autowired
    private VkApiService vkApiService;

    @Test
    public void should_send_test_request() throws InterruptedException {
        vkApiService.getWallMessages(response -> {
            log.info("result={}", response);
        });
        Thread.sleep(5000);
    }
}