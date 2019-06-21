package ru.skogmark.aggregator.core.content;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ParsingContext {
    private final Integer sourceId;
    private final Integer limit;
    private final Long offset;

    private ParsingContext(@Nonnull Integer sourceId,
                           @Nonnull Integer limit,
                           @Nullable Long offset) {
        this.sourceId = requireNonNull(sourceId, "sourceId");
        this.limit = requireNonNull(limit, "limit");
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "ParsingContext{" +
                "sourceId=" + sourceId +
                ", limit=" + limit +
                ", offset=" + offset +
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer sourceId;
        private Integer limit;
        private Long offset;

        private Builder() {
        }

        public ParsingContext build() {
            return new ParsingContext(sourceId, limit, offset);
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
    }
}
