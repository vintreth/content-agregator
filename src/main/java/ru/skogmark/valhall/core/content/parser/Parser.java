package ru.skogmark.valhall.core.content.parser;

import ru.skogmark.valhall.core.content.Content;
import ru.skogmark.valhall.core.content.AuthorizationMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface Parser {
    void auth(@Nullable AuthorizationMeta authorizationMeta, @Nonnull ParserAuthorizationListener listener);

    Optional<Content> parse(long offset);
}
