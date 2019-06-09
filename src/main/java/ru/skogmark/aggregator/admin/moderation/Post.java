package ru.skogmark.aggregator.admin.moderation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Post {
    private final Long id;
    private final String channel;
    private final String text;
    private final List<String> images;
    private final String createdDt;

    private Post(@Nullable Long id,
                 @Nonnull String channel,
                 @Nonnull String text,
                 @Nullable List<String> images,
                 @Nullable String createdDt) {
        this.id = id;
        this.channel = requireNonNull(channel, "channel");
        this.text = requireNonNull(text, "text");
        this.images = images == null ? Collections.emptyList() : List.copyOf(images);
        this.createdDt = createdDt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", channel='" + channel + '\'' +
                ", text='" + text + '\'' +
                ", images=" + images +
                ", createdDt='" + createdDt + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getChannel() {
        return channel;
    }

    public String getText() {
        return text;
    }

    public List<String> getImages() {
        return images;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String channel;
        private String text;
        private List<String> images;
        private String createdDt;

        private Builder() {
        }

        public Post build() {
            return new Post(id, channel, text, images, createdDt);
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
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

        public Builder setCreatedDt(String createdDt) {
            this.createdDt = createdDt;
            return this;
        }
    }
}
