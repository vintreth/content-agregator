package ru.skogmark.aggregator.parser.vk;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class GetWallRequest {
    private final Owner owner;
    private final Integer count;
    private final Long offset;

    private GetWallRequest(@Nonnull Owner owner, @Nonnull Integer count, @Nullable Long offset) {
        this.owner = requireNonNull(owner, "owner");
        this.count = requireNonNull(count, "count");
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "GetWallRequest{" +
                "owner=" + owner +
                ", count=" + count +
                ", offset=" + offset +
                '}';
    }

    @Nonnull
    Owner getOwner() {
        return owner;
    }

    @Nonnull
    Integer getCount() {
        return count;
    }

    @Nonnull
    Optional<Long> getOffset() {
        return Optional.ofNullable(offset);
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private Owner owner;
        private Integer count;
        private Long offset;

        private Builder() {
        }

        GetWallRequest build() {
            return new GetWallRequest(owner, count, offset);
        }

        Builder setOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        Builder setCount(Integer count) {
            this.count = count;
            return this;
        }

        Builder setOffset(Long offset) {
            this.offset = offset;
            return this;
        }
    }
}
