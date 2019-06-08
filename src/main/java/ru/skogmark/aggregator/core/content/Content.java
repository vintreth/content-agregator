package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Content {
    private final List<ContentItem> items;
    private final Long nextOffset;

    public Content(@Nonnull List<ContentItem> items, @Nonnull Long nextOffset) {
        this.items = Collections.unmodifiableList(requireNonNull(items, "items"));
        this.nextOffset = requireNonNull(nextOffset, "nextOffset");
    }

    @Override
    public String toString() {
        return "Content{" +
                "items=" + items +
                ", nextOffset=" + nextOffset +
                '}';
    }

    @Nonnull
    public List<ContentItem> getItems() {
        return items;
    }

    @Nonnull
    public Long getNextOffset() {
        return nextOffset;
    }
}
