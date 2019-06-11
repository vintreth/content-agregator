package ru.skogmark.aggregator.core.content;

import ru.skogmark.aggregator.core.PostImage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ContentPost {
    private final Long externalId;
    private final String title;
    private final String text;
    private final List<PostImage> images;

    private ContentPost(@Nonnull Long externalId,
                        @Nullable String title,
                        @Nullable String text,
                        @Nullable List<PostImage> images) {
        this.externalId = requireNonNull(externalId, "externalId");
        this.title = title;
        this.text = text;
        this.images = images == null ? Collections.emptyList() : Collections.unmodifiableList(images);
    }

    @Override
    public String toString() {
        return "ContentPost{" +
                "externalId=" + externalId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", images=" + images +
                '}';
    }

    @Nonnull
    public Long getExternalId() {
        return externalId;
    }

    @Nonnull
    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    @Nonnull
    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    @Nonnull
    public List<PostImage> getImages() {
        return images;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long externalId;
        private String title;
        private String text;
        private List<PostImage> images;

        private Builder() {
        }

        public ContentPost build() {
            return new ContentPost(externalId, title, text, images);
        }

        public Builder setExternalId(Long externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setImages(List<PostImage> images) {
            this.images = images;
            return this;
        }
    }
}
