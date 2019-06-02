package ru.skogmark.aggregator.core;

import javax.annotation.Nonnull;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ChannelContext {
    private final int channelId;
    private final Set<SourceContext> sourceContexts;

    private ChannelContext(int channelId, @Nonnull Set<SourceContext> sourceContexts) {
        this.channelId = channelId;
        this.sourceContexts = Set.copyOf(requireNonNull(sourceContexts, "sourceContexts"));
    }

    @Override
    public String toString() {
        return "ChannelContextImpl{" +
                "channelId=" + channelId +
                ", sourceContexts=" + sourceContexts +
                '}';
    }

    public int getChannelId() {
        return channelId;
    }

    @Nonnull
    public Set<SourceContext> getSourceContexts() {
        return sourceContexts;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer channelId;
        private Set<SourceContext> sourceContexts;

        private Builder() {
        }

        public ChannelContext build() {
            return new ChannelContext(channelId, sourceContexts);
        }

        public Builder withChannelId(Integer channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder withSources(Set<SourceContext> sourceContexts) {
            this.sourceContexts = sourceContexts;
            return this;
        }
    }
}
