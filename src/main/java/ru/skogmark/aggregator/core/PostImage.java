package ru.skogmark.aggregator.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class PostImage {
    private final String src;
    private final Integer width;
    private final Integer height;

    @JsonCreator
    private PostImage(@JsonProperty("src") @Nonnull String src,
                      @JsonProperty("width") @Nullable Integer width,
                      @JsonProperty("height") @Nullable Integer height) {
        this.src = requireNonNull(src, "src");
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "PostImage{" +
                "src='" + src + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
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
        private String src;
        private Integer width;
        private Integer height;

        private Builder() {
        }

        public PostImage build() {
            return new PostImage(src, width, height);
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
