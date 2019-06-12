package ru.skogmark.aggregator.core.moderation;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

import static ru.skogmark.aggregator.AggregatorApplication.PROFILE_TEST;

@Service
@Primary
@Profile(PROFILE_TEST)
public class PremoderationQueueServiceStub implements PremoderationQueueService {
    private List<UnmoderatedPost> posts;

    public void setPosts(List<UnmoderatedPost> posts) {
        this.posts = posts;
    }

    @Override
    public List<UnmoderatedPost> getPosts(int limit, long offset) {
        return posts;
    }

    @Override
    public void enqueuePosts(@Nonnull List<UnmoderatedPost> posts) {
        // doing nothing
    }

    @Override
    public Optional<UnmoderatedPost> getPost(long id) {
        return Optional.empty();
    }

    @Override
    public long getPostsCount() {
        return 0;
    }

    @Override
    public boolean updatePost(@Nonnull UnmoderatedPost unmoderatedPost) {
        return false;
    }
}
