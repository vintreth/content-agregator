package ru.skogmark.valhall.core.content;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface ParsedContentListener {
    void onContentParsed(@Nonnull Content content);
}
