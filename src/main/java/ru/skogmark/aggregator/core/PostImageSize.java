package ru.skogmark.aggregator.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class PostImageSize {
    private final String uuid;
    private final String src;
    private final Integer width;
    private final Integer height;

    @JsonCreator
    private PostImageSize(@JsonProperty("uuid") @Nonnull String uuid,
                          @JsonProperty("src") @Nonnull String src,
                          @JsonProperty("width") @Nullable Integer width,
                          @JsonProperty("height") @Nullable Integer height) {
        this.uuid = requireNonNull(uuid, "uuid");
        this.src = requireNonNull(src, "src");
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "PostImageSize{" +
                "uuid='" + uuid + '\'' +
                ", src='" + src + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @JsonProperty("uuid")
    @Nonnull
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("src")
    @Nonnull
    public String getSrc() {
        return src;
    }

    @JsonProperty("width")
    @Nonnull
    public Optional<Integer> getWidth() {
        return Optional.ofNullable(width);
    }

    @JsonProperty("height")
    @Nonnull
    public Optional<Integer> getHeight() {
        return Optional.ofNullable(height);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String uuid;
        private String src;
        private Integer width;
        private Integer height;

        private Builder() {
        }

        public PostImageSize build() {
            return new PostImageSize(uuid, src, width, height);
        }

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setSrc(String src) {
            this.src = src;
            return this;
        }

        public Builder setWidth(Integer width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(Integer height) {
            this.height = height;
            return this;
        }
    }
}
