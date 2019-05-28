package ru.skogmark.aggregator.core;

import javax.annotation.Nonnull;
import java.util.Set;

public interface ChannelContext {
    int getId();

    @Nonnull
    String getName();

    @Nonnull
    Set<SourceContext> getSourceContexts();
}
