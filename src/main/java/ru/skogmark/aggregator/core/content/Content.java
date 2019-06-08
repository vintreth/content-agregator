package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Content {
    private final List<ContentPost> posts;
    private final Long nextOffset;

    public Content(@Nonnull List<ContentPost> posts, @Nonnull Long nextOffset) {
        this.posts = Collections.unmodifiableList(requireNonNull(posts, "posts"));
        this.nextOffset = requireNonNull(nextOffset, "nextOffset");
    }

    @Override
    public String toString() {
        return "Content{" +
                "posts=" + posts +
                ", nextOffset=" + nextOffset +
                '}';
    }

    @Nonnull
    public List<ContentPost> getPosts() {
        return posts;
    }

    @Nonnull
    public Long getNextOffset() {
        return nextOffset;
    }
}
