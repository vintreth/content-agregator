package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

class Photo {
    private final List<Size> sizes;

    @JsonCreator
    Photo(@JsonProperty("sizes") @Nullable List<Size> sizes) {
        this.sizes = sizes == null ? Collections.emptyList() : Collections.unmodifiableList(sizes);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "sizes=" + sizes +
                '}';
    }

    @Nonnull
    List<Size> getSizes() {
        return sizes;
    }
}
