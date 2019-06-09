package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class Item {
    private final Long id;
    private final String text;
    private final List<Attachment> attachments;

    @JsonCreator
    Item(@JsonProperty("id") @Nonnull Long id,
         @JsonProperty("text") @Nullable String text,
         @JsonProperty("attachments") @Nullable List<Attachment> attachments) {
        this.id = requireNonNull(id, "id");
        this.text = text;
        this.attachments = attachments == null ? Collections.emptyList() : Collections.unmodifiableList(attachments);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", attachments='" + attachments + '\'' +
                '}';
    }

    @Nonnull
    Long getId() {
        return id;
    }

    @Nonnull
    Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    @Nonnull
    List<Attachment> getAttachments() {
        return attachments;
    }
}
