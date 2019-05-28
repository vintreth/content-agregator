package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface ContentListener {
    void onContentParsed(@Nonnull Content content);
}
