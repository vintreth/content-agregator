package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public class ParsingContext {
    private final Integer sourceId;
    private final Integer limit;
    private final Long offset;
    private final Consumer<Content> onContentReceivedCallback;

    private ParsingContext(@Nonnull Integer sourceId,
                           @Nonnull Integer limit,
                           @Nullable Long offset,
                           @Nullable Consumer<Content> onContentReceivedCallback) {
        this.sourceId = requireNonNull(sourceId, "sourceId");
        this.limit = requireNonNull(limit, "limit");
        this.offset = offset;
        this.onContentReceivedCallback = onContentReceivedCallback;
    }

    @Override
    public String toString() {
        return "ParsingContext{" +
                "sourceId=" + sourceId +
                ", limit=" + limit +
                ", offset=" + offset +
                ", onContentReceivedCallback=" + onContentReceivedCallback +
                '}';
    }

    @Nonnull
    public Integer getSourceId() {
        return sourceId;
    }

    @Nonnull
    public Integer getLimit() {
        return limit;
    }

    @Nonnull
    public Optional<Long> getOffset() {
        return Optional.ofNullable(offset);
    }

    @Nonnull
    public Consumer<Content> getOnContentReceivedCallback() {
        return onContentReceivedCallback;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer sourceId;
        private Integer limit;
        private Long offset;
        private Consumer<Content> onContentReceivedCallback;

        private Builder() {
        }

        public ParsingContext build() {
            return new ParsingContext(sourceId, limit, offset, onContentReceivedCallback);
        }

        public Builder setSourceId(Integer sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder setOffset(Long offset) {
            this.offset = offset;
            return this;
        }

        public Builder setOnContentReceivedCallback(Consumer<Content> onContentReceivedCallback) {
            this.onContentReceivedCallback = onContentReceivedCallback;
            return this;
        }
    }
}
