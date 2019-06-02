package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

class VkApiResult {
    // todo add error field
    private final Response response;

    @JsonCreator
    private VkApiResult(@JsonProperty("response") @Nullable Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "VkApiResult{" +
                "response=" + response +
                '}';
    }

    @Nonnull
    Optional<Response> getResponse() {
        return Optional.ofNullable(response);
    }

    boolean isError() {
        return false;
    }
}
