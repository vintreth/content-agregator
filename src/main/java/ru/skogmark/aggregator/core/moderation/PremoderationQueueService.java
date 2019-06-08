package ru.skogmark.aggregator.core.moderation;

import javax.annotation.Nonnull;
import java.util.List;

public interface PremoderationQueueService {
    void enqueuePosts(@Nonnull List<UnmoderatedPost> posts);

    List<UnmoderatedPost> getPosts(int limit, long offset);

    long getPostsCount();
}
