package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;

public interface Parser {
    void parse(@Nonnull ParsingContext parsingContext);
}
