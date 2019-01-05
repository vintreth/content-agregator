package ru.skogmark.valhall.core.content;

import javax.annotation.Nonnull;

public interface ParserFactory {
    @Nonnull
    Parser getParser(int sourceId);
}
