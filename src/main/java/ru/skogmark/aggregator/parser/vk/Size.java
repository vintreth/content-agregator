package ru.skogmark.aggregator.parser.vk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

class Size {
    private final String type;
    private final String url;
    private final Integer width;
    private final Integer height;

    @JsonCreator
    Size(@JsonProperty("type") @Nonnull String type,
         @JsonProperty("url") @Nonnull String url,
         @JsonProperty("width") @Nonnull Integer width,
         @JsonProperty("height") @Nonnull Integer height) {
        this.type = requireNonNull(type, "type");
        this.url = requireNonNull(url, "url");
        this.width = requireNonNull(width, "width");
        this.height = requireNonNull(height, "height");
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

    @JsonProperty("type")
    @Nonnull
    String getType() {
        return type;
    }

    @JsonProperty("url")
    @Nonnull
    String getUrl() {
        return url;
    }

    @JsonProperty("width")
    @Nonnull
    Integer getWidth() {
        return width;
    }

    @JsonProperty("height")
    @Nonnull
    Integer getHeight() {
        return height;
    }
}
