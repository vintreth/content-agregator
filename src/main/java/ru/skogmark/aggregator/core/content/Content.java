package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import java.net.URI;

import static java.util.Objects.requireNonNull;

public class Content {
    private final long externalId;
    private final String text;
    private final URI imageUri;

    private Content(long externalId, @Nonnull String text, @Nonnull URI imageUri) {
        this.externalId = externalId;
        this.text = requireNonNull(text, "text");
        this.imageUri = requireNonNull(imageUri, "imageUri");
    }

    @Override
    public String toString() {
        return "Content{" +
                "externalId=" + externalId +
                ", text='" + text + '\'' +
                ", imageUri=" + imageUri +
                '}';
    }

    public long getExternalId() {
        return externalId;
    }

    @Nonnull
    public String getText() {
        return text;
    }

    @Nonnull
    public URI getImageUri() {
        return imageUri;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long externalId;
        private String text;
        private URI imageUri;

        private Builder() {
        }

        public Content build() {
            return new Content(externalId, text, imageUri);
        }

        public Builder setExternalId(Long externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setImageUri(URI imageUri) {
            this.imageUri = imageUri;
            return this;
        }
    }
}
