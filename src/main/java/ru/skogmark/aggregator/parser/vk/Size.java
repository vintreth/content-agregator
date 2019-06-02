package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

class Size {
    private final String type;
    private final String url;
    private final int width;
    private final int height;

    @JsonCreator
    private Size(@JsonProperty("type") @Nonnull String type,
                 @JsonProperty("url") @Nonnull String url,
                 @JsonProperty("width") int width,
                 @JsonProperty("height") int height) {
        this.type = requireNonNull(type, "type");
        this.url = requireNonNull(url, "url");
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Size{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Nonnull
    String getType() {
        return type;
    }

    @Nonnull
    String getUrl() {
        return url;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }
}
