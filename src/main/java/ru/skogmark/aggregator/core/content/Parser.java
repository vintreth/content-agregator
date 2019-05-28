package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface Parser {
    void auth(@Nullable AuthorizationMeta authorizationMeta, @Nonnull ParserAuthorizationListener listener);

    Optional<Content> parse(long offset); // todo add parsing limit
}
