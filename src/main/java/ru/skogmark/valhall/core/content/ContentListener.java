package ru.skogmark.valhall.core.content;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface ContentListener {
    void onContentParsed(@Nonnull Content content);
}
