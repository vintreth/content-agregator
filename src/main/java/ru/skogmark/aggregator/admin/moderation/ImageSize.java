package ru.skogmark.aggregator.admin.moderation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

public class ImageSize {
    private final String uuid;
    private final String src;
    private final String title;

    public ImageSize(@Nonnull String uuid, @Nonnull String src, @Nullable String title) {
        this.uuid = requireNonNull(uuid, "uuid");
        this.src = requireNonNull(src, "src");
        this.title = title;
    }

    @Override
    public String toString() {
        return "ImageSize{" +
                "uuid=" + uuid +
                ", src='" + src + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Nonnull
    public String getUuid() {
        return uuid;
    }

    @Nonnull
    public String getSrc() {
        return src;
    }

    @Nullable
    public String getTitle() {
        return title;
    }
}
