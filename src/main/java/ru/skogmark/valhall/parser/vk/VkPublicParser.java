package ru.skogmark.valhall.parser.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skogmark.valhall.core.content.AuthorizationMeta;
import ru.skogmark.valhall.core.content.Content;
import ru.skogmark.valhall.core.content.Parser;
import ru.skogmark.valhall.core.content.ParserAuthorizationListener;

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
