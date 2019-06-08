package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ContentItem {
    private final Long externalId;
    private final String text;
    private final List<String> images;

    private ContentItem(@Nonnull Long externalId, @Nonnull String text, @Nullable List<String> images) {
        this.externalId = requireNonNull(externalId, "externalId");
        this.text = requireNonNull(text, "text");
        this.images = images == null ? Collections.emptyList() : Collections.unmodifiableList(images);
    }

    @Override
    public String toString() {
        return "ContentItem{" +
                "externalId=" + externalId +
                ", text='" + text + '\'' +
                ", images=" + images +
                '}';
    }

    @Nonnull
    public Long getExternalId() {
        return externalId;
    }

    @Nonnull
    public String getText() {
        return text;
    }

    @Nonnull
    public List<String> getImages() {
        return images;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long externalId;
        private String text;
        private List<String> images;

        private Builder() {
        }

        public ContentItem build() {
            return new ContentItem(externalId, text, images);
        }

        public Builder setExternalId(Long externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setImages(List<String> images) {
            this.images = images;
            return this;
        }
    }
}
