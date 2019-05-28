package ru.skogmark.aggregator.core.premoderation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

public class UnmoderatedPost {
    @Nullable
    private final Long id;

    @Nullable
    private final String text;

    @Nullable
    private final Long imageId;

    @Nullable
    private final ZonedDateTime createdDt;

    private UnmoderatedPost(Long id, String text, Long imageId, ZonedDateTime createdDt) {
        this.id = id;
        this.text = text;
        this.imageId = imageId;
        this.createdDt = createdDt;
    }

    @Nonnull
    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    @Nonnull
    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    @Nonnull
    public Optional<Long> getImageId() {
        return Optional.ofNullable(imageId);
    }

    @Nullable
    public Optional<ZonedDateTime> getCreatedDt() {
        return Optional.ofNullable(createdDt);
    }

    @Override
    public String toString() {
        return "UnmoderatedPost{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", imageId=" + imageId +
                ", createdDt=" + createdDt +
                '}';
    }

    public Builder copy() {
        return builder()
                .id(id)
                .text(text)
                .imageId(imageId)
                .createdDt(createdDt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String text;
        private Long imageId;
        private ZonedDateTime createdDt;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder imageId(Long imageId) {
            this.imageId = imageId;
            return this;
        }

        public Builder createdDt(ZonedDateTime createdDt) {
            this.createdDt = createdDt;
            return this;
        }

        public UnmoderatedPost build() {
            return new UnmoderatedPost(id, text, imageId, createdDt);
        }
    }
}
