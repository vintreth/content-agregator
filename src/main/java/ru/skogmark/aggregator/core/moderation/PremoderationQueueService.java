package ru.skogmark.aggregator.core.moderation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface PremoderationQueueService {
    Optional<UnmoderatedPost> getPost(long id);

    List<UnmoderatedPost> getPosts(int limit, long offset);

    long getPostsCount();

    void enqueuePosts(@Nonnull List<UnmoderatedPost> posts);
}
