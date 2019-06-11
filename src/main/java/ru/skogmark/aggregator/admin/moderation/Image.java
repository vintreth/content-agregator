package ru.skogmark.aggregator.admin.moderation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

public class Image {
    private final String src;
    private final String title;

    public Image(@Nonnull String src, @Nullable String title) {
        this.src = requireNonNull(src, "src");
        this.title = title;
    }

    @Override
    public String toString() {
        return "Image{" +
                "src='" + src + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getSrc() {
        return src;
    }

    public String getTitle() {
        return title;
    }
}
