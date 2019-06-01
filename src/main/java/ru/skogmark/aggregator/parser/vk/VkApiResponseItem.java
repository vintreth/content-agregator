package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class VkApiResponseItem {
    private final Long id;
    private final String text;

    @JsonCreator
    public VkApiResponseItem(@JsonProperty("id") @Nonnull Long id,
                             @JsonProperty("text") @Nonnull String text) {
        this.id = requireNonNull(id, "id");
        this.text = requireNonNull(text, "text");
    }

    @Override
    public String toString() {
        return "VkApiResponseItem{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
