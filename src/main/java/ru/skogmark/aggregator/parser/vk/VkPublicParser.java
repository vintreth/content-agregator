package ru.skogmark.aggregator.parser.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skogmark.aggregator.core.content.AuthorizationMeta;
import ru.skogmark.aggregator.core.content.Content;
import ru.skogmark.aggregator.core.content.Parser;
import ru.skogmark.aggregator.core.content.ParserAuthorizationListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class VkPublicParser implements Parser {
    private static final Logger log = LoggerFactory.getLogger(VkPublicParser.class);

    @Override
    public void auth(@Nullable AuthorizationMeta authorizationMeta, @Nonnull ParserAuthorizationListener listener) {
    }

    @Override
    public Optional<Content> parse(long offset) {
        return Optional.empty();
    }
}
