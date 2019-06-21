package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Parser {
    @Nonnull
    Optional<Content> parse(@Nonnull ParsingContext parsingContext);
}
