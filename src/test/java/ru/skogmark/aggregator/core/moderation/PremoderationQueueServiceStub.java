package ru.skogmark.aggregator.core.moderation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;
import java.util.List;

import static ru.skogmark.aggregator.AggregatorApplication.PROFILE_TEST;

@Service
@Profile(PROFILE_TEST)
public class PremoderationQueueServiceStub implements PremoderationQueueService {
    @Override
    public List<UnmoderatedPost> getPosts(int limit, long offset) {
        return List.of(
                UnmoderatedPost.builder()
                        .setId(1L)
                        .setChannelId(1)
                        .setText("Text of the first post")
                        .setImages(List.of("img1", "img2"))
                        .setCreatedDt(ZonedDateTime.now())
                        .build());
    }

    @Override
    public void enqueuePosts(@Nonnull List<UnmoderatedPost> posts) {

    }

    @Override
    public long getPostsCount() {
        return 0;
    }
}
