package ru.skogmark.valhall.parser.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skogmark.valhall.core.content.AuthorizationMeta;
import ru.skogmark.valhall.core.content.Content;
import ru.skogmark.valhall.core.content.Parser;
import ru.skogmark.valhall.core.content.ParserAuthorizationListener;
import ru.skogmark.valhall.utils.http.HttpClient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static java.util.Objects.requireNonNull;

@Service
public class VkPublicParser implements Parser {
    private static final Logger log = LoggerFactory.getLogger(VkPublicParser.class);

    private final ExecutorService outputRequestExecutor;
    private final HttpClient httpClient;

    public VkPublicParser(@Nonnull ExecutorService outputRequestExecutor,
                          @Nonnull HttpClient httpClient) {
        this.outputRequestExecutor = requireNonNull(outputRequestExecutor, "outputRequestExecutor");
        this.httpClient = requireNonNull(httpClient, "httpClient");
    }

    @Override
    public void auth(@Nullable AuthorizationMeta authorizationMeta, @Nonnull ParserAuthorizationListener listener) {
        outputRequestExecutor.execute(() -> {
            log.debug("Starting to authorize in VK");
            String content = httpClient.doGet("https://api.vk.com/method/users.getSubscriptions?user_ids=210700286&access_token=-1&v=5.92");
            log.debug(content);
        });
    }

    @Override
    public Optional<Content> parse(long offset) {
        return Optional.empty();
    }
}
