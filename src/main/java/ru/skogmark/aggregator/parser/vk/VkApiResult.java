package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class VkApiResult {
    private final VkApiResponse response;

    @JsonCreator
    public VkApiResult(@JsonProperty("response") @Nonnull VkApiResponse response) {
        this.response = requireNonNull(response, "response");
    }

    @Override
    public String toString() {
        return "VkApiResult{" +
                "response=" + response +
                '}';
    }
}
