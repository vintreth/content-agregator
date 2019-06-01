package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class VkApiResponse {
    private final Integer count;
    private final List<VkApiResponseItem> items;

    @JsonCreator
    public VkApiResponse(@JsonProperty("count") @Nonnull Integer count,
                         @JsonProperty("items") @Nonnull List<VkApiResponseItem> items) {
        this.count = requireNonNull(count, "count");
        this.items = Collections.unmodifiableList(requireNonNull(items, "items"));
    }

    @Override
    public String toString() {
        return "VkApiResponse{" +
                "count=" + count +
                ", items=" + items +
                '}';
    }
}
