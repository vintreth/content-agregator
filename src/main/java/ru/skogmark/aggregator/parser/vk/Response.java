package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

class Response {
    private final Integer count;
    private final List<Item> items;

    @JsonCreator
    private Response(@JsonProperty("count") @Nonnull Integer count,
                     @JsonProperty("items") @Nonnull List<Item> items) {
        this.count = requireNonNull(count, "count");
        this.items = Collections.unmodifiableList(requireNonNull(items, "items"));
    }

    @Override
    public String toString() {
        return "Response{" +
                "count=" + count +
                ", items=" + items +
                '}';
    }

    @Nonnull
    Integer getCount() {
        return count;
    }

    @Nonnull
    List<Item> getItems() {
        return items;
    }
}
