package ru.skogmark.aggregator.admin.moderation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class Image {
    private final List<ImageSize> sizes;

    public Image(@Nullable List<ImageSize> sizes) {
        this.sizes = sizes != null ? List.copyOf(sizes) : Collections.emptyList();
    }

    @Override
    public String toString() {
        return "Image{" +
                "sizes=" + sizes +
                '}';
    }

    @Nonnull
    public List<ImageSize> getSizes() {
        return sizes;
    }
}
