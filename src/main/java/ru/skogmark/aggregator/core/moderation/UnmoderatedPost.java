package ru.skogmark.aggregator.core.moderation;

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
    private final String text;
    private final List<String> images;
    private final ZonedDateTime createdDt;

    private UnmoderatedPost(@Nullable Long id,
                            @Nonnull Integer channelId,
                            @Nonnull String text,
                            @Nullable List<String> images,
                            @Nullable ZonedDateTime createdDt) {
        this.id = id;
        this.channelId = requireNonNull(channelId, "channelId");
        this.text = requireNonNull(text, "text");
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
    public String getText() {
        return text;
    }

    @Nonnull
    public List<String> getImages() {
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
                ", text='" + text + '\'' +
                ", images=" + images +
                ", createdDt=" + createdDt +
                '}';
    }

    public Builder copy() {
        return builder()
                .setId(id)
                .setChannelId(channelId)
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
        private String text;
        private List<String> images;
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

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setImages(List<String> images) {
            this.images = images;
            return this;
        }

        public Builder setCreatedDt(ZonedDateTime createdDt) {
            this.createdDt = createdDt;
            return this;
        }

        public UnmoderatedPost build() {
            return new UnmoderatedPost(id, channelId, text, images, createdDt);
        }
    }
}
