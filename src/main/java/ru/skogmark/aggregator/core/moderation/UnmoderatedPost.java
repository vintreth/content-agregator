package ru.skogmark.aggregator.core.moderation;

import ru.skogmark.aggregator.core.PostImage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class UnmoderatedPost {
    private final Long id;
    private final Integer channelId;
    private final String title;
    private final String text;
    private final List<PostImage> images;
    private final ZonedDateTime createdDt;

    private UnmoderatedPost(@Nullable Long id,
                            @Nonnull Integer channelId,
                            @Nullable String title,
                            @Nullable String text,
                            @Nullable List<PostImage> images,
                            @Nullable ZonedDateTime createdDt) {
        this.id = id;
        this.channelId = requireNonNull(channelId, "channelId");
        this.title = title;
        this.text = text;
        this.images = images == null ? Collections.emptyList() : Collections.unmodifiableList(images);
        this.createdDt = createdDt;
    }

    @Nonnull
    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    @Nonnull
    public Integer getChannelId() {
        return channelId;
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

    @Nonnull
    public Optional<ZonedDateTime> getCreatedDt() {
        return Optional.ofNullable(createdDt);
    }

    @Override
    public String toString() {
        return "UnmoderatedPost{" +
                "id=" + id +
                ", channelId=" + channelId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", images=" + images +
                ", createdDt=" + createdDt +
                '}';
    }

    public Builder copy() {
        return builder()
                .setId(id)
                .setChannelId(channelId)
                .setTitle(title)
                .setText(text)
                .setImages(images)
                .setCreatedDt(createdDt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Integer channelId;
        private String title;
        private String text;
        private List<PostImage> images;
        private ZonedDateTime createdDt;

        private Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setChannelId(Integer channelId) {
            this.channelId = channelId;
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

        public Builder setCreatedDt(ZonedDateTime createdDt) {
            this.createdDt = createdDt;
            return this;
        }

        public UnmoderatedPost build() {
            return new UnmoderatedPost(id, channelId, title, text, images, createdDt);
        }
    }
}
