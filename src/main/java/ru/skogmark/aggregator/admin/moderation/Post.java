package ru.skogmark.aggregator.admin.moderation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Post {
    private final Long id;
    private final String channel;
    private final Integer channelId;
    private final String title;
    private final String text;
    private final List<Image> images;
    private final String createdDt;
    private final String changedDt;

    private Post(@Nonnull Long id,
                 @Nonnull String channel,
                 @Nonnull Integer channelId,
                 @Nullable String title,
                 @Nullable String text,
                 @Nullable List<Image> images,
                 @Nullable String createdDt,
                 @Nullable String changedDt) {
        this.id = requireNonNull(id, "id");
        this.channel = requireNonNull(channel, "channel");
        this.channelId = requireNonNull(channelId, "channelId");
        this.title = title;
        this.text = text;
        this.images = images == null ? Collections.emptyList() : List.copyOf(images);
        this.createdDt = createdDt;
        this.changedDt = changedDt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", channel='" + channel + '\'' +
                ", channelId=" + channelId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", images=" + images +
                ", createdDt='" + createdDt + '\'' +
                ", changedDt='" + changedDt + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getChannel() {
        return channel;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<Image> getImages() {
        return images;
    }

    public int getImagesSize() {
        return images.size();
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public String getChangedDt() {
        return changedDt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String channel;
        private Integer channelId;
        private String title;
        private String text;
        private List<Image> images;
        private String createdDt;
        private String changedDt;

        private Builder() {
        }

        public Post build() {
            return new Post(id, channel, channelId, title, text, images, createdDt, changedDt);
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
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

        public Builder setImages(List<Image> images) {
            this.images = images;
            return this;
        }

        public Builder setCreatedDt(String createdDt) {
            this.createdDt = createdDt;
            return this;
        }

        public Builder setChangedDt(String changedDt) {
            this.changedDt = changedDt;
            return this;
        }
    }
}
