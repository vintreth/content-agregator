package ru.skogmark.valhall.core.content.parser;

import ru.skogmark.valhall.core.content.Content;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface ParserContentListener {
    void onContentObtained(@Nonnull Content content);
}
