package ru.skogmark.aggregator.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class PostImage {
    private final List<PostImageSize> sizes;

    @JsonCreator
    public PostImage(@JsonProperty("sizes") @Nullable List<PostImageSize> sizes) {
        this.sizes = sizes != null ? List.copyOf(sizes) : Collections.emptyList();
    }

    @Override
    public String toString() {
        return "PostImage{" +
                "sizes=" + sizes +
                '}';
    }

    @Nonnull
    public List<PostImageSize> getSizes() {
        return sizes;
    }
}
