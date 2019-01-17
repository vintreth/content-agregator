package ru.skogmark.valhall.parser.vk;

import org.junit.Test;
import ru.skogmark.valhall.SyncTestExecutor;
import ru.skogmark.valhall.utils.http.HttpClient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class VkPublicParserTest {
    @Test
    public void should_authorize_parser() {
        VkPublicParser parser = new VkPublicParser(new SyncTestExecutor(), new HttpClient());
        parser.auth(null, authorizationMeta -> {
        });
    }
}