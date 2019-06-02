package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class Attachment {
    private final String type;
    private final Photo photo;

    @JsonCreator
    private Attachment(@JsonProperty("type") @Nonnull String type,
                       @JsonProperty("photo") @Nullable Photo photo) {
        this.type = requireNonNull(type, "type");
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "photo=" + photo +
                '}';
    }

    @Nonnull
    String getType() {
        return type;
    }

    @Nonnull
    Optional<Photo> getPhoto() {
        return Optional.ofNullable(photo);
    }
}
