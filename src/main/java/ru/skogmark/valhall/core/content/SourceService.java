package ru.skogmark.valhall.core.content;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class SourceService {
    private final Parser parser;

    public SourceService(@Nonnull Parser parser) {
        this.parser = requireNonNull(parser, "parser");
    }

    public Optional<Content> parseNext() {
        return Optional.empty();
    }
}
