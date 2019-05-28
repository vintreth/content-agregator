package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import java.net.URI;

import static java.util.Objects.requireNonNull;

public class Content {
    @Nonnull
    private final String text;

    @Nonnull
    private final URI imageUri;

    private Content(@Nonnull String text, @Nonnull URI imageUri) {
        this.text = requireNonNull(text, "text");
        this.imageUri = requireNonNull(imageUri, "imageUri");
    }

    public static Content of(@Nonnull String text, @Nonnull URI imageUri) {
        return new Content(text, imageUri);
    }

    @Override
    public String toString() {
        return "Content{" +
                "text='" + text + '\'' +
                ", imageUri=" + imageUri +
                '}';
    }

    @Nonnull
    public String getText() {
        return text;
    }

    @Nonnull
    public URI getImageUri() {
        return imageUri;
    }
}
