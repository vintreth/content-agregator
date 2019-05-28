package ru.skogmark.aggregator.channel;

import ru.skogmark.aggregator.core.ChannelContext;
import ru.skogmark.aggregator.core.SourceContext;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ChannelContextImpl implements ChannelContext {
    @Nonnull
    private final Channel channel;

    @Nonnull
    private final Set<SourceContext> sourceContexts;

    private ChannelContextImpl(@Nonnull Channel channel, @Nonnull Set<SourceContext> sourceContexts) {
        this.channel = requireNonNull(channel, "channel");
        this.sourceContexts = Collections.unmodifiableSet(new HashSet<>(requireNonNull(sourceContexts, "sourceContexts")));
    }

    @Override
    public String toString() {
        return "ChannelContextImpl{" +
                "channel=" + channel +
                ", sourceContexts=" + sourceContexts +
                '}';
    }

    @Override
    public int getId() {
        return channel.getId();
    }

    @Nonnull
    @Override
    public String getName() {
        return channel.getName();
    }

    @Nonnull
    public Set<SourceContext> getSourceContexts() {
        return sourceContexts;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Channel channel;
        private Set<SourceContext> sourceContexts;

        private Builder() {
        }

        public ChannelContextImpl build() {
            return new ChannelContextImpl(channel, sourceContexts);
        }

        public Builder channel(Channel channel) {
            this.channel = channel;
            return this;
        }

        public Builder sources(Set<SourceContext> sourceContexts) {
            this.sourceContexts = sourceContexts;
            return this;
        }
    }
}
