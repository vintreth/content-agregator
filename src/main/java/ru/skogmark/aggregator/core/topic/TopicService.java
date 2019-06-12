package ru.skogmark.aggregator.core.topic;

import javax.annotation.Nonnull;

public interface TopicService {
    @Nonnull
    TopicPost addPost(@Nonnull TopicPost post);
}
