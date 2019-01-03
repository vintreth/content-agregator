package ru.skogmark.valhall.core.content.parser;

import ru.skogmark.valhall.core.content.Source;

import javax.annotation.Nonnull;

public interface ParserFactory {
    @Nonnull
    Parser getParser(Source source);
}
