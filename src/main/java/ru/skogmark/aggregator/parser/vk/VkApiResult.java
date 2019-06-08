package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

class VkApiResult {
    private final Response response;
    private final VkApiError error;

    @JsonCreator
    VkApiResult(@JsonProperty("response") @Nullable Response response,
                @JsonProperty("error") @Nullable VkApiError error) {
        this.response = response;
        this.error = error;
    }

    @Override
    public String toString() {
        return "VkApiResult{" +
                "response=" + response +
                ", error=" + error +
                '}';
    }

    @Nonnull
    Optional<Response> getResponse() {
        return Optional.ofNullable(response);
    }

    @Nonnull
    Optional<VkApiError> getError() {
        return Optional.ofNullable(error);
    }

    boolean isError() {
        return error != null;
    }
}
