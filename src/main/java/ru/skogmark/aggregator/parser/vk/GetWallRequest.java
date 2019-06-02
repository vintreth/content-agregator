package ru.skogmark.aggregator.parser.vk;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

class GetWallRequest {
    private final Owner owner;
    private final int count;
    private final long offset;

    private GetWallRequest(@Nonnull Owner owner, int count, long offset) {
        this.owner = requireNonNull(owner, "owner");
        this.count = count;
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

    int getCount() {
        return count;
    }

    long getOffset() {
        return offset;
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

        Builder withOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        Builder withCount(Integer count) {
            this.count = count;
            return this;
        }

        Builder withOffset(Long offset) {
            this.offset = offset;
            return this;
        }
    }
}
