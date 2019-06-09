package ru.skogmark.aggregator.core.moderation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static ru.skogmark.aggregator.AggregatorApplication.PROFILE_TEST;

@Service
@Profile(PROFILE_TEST)
public class PremoderationQueueServiceStub1 implements PremoderationQueueService {
    @Override
    public List<UnmoderatedPost> getPosts(int limit, long offset) {
        ZonedDateTime now = ZonedDateTime.now();
        return List.of(
                UnmoderatedPost.builder()
                        .setId(1L)
                        .setChannelId(1)
                        .setText("Text of the first post")
                        .setImages(List.of("img1", "img2"))
                        .setCreatedDt(now)
                        .build(),
                UnmoderatedPost.builder()
                        .setId(2L)
                        .setChannelId(1)
                        .setText("Text of the second post")
                        .setImages(List.of("img3", "img4"))
                        .setCreatedDt(now)
                        .build(),
                UnmoderatedPost.builder()
                        .setId(3L)
                        .setChannelId(1)
                        .setText("Text of the third post")
                        .setImages(List.of("img5", "img6"))
                        .setCreatedDt(now)
                        .build());
    }

    @Override
    public void enqueuePosts(@Nonnull List<UnmoderatedPost> posts) {
        // doing nothing
    }

    @Override
    public Optional<UnmoderatedPost> getPost(long id) {
        return Optional.of(UnmoderatedPost.builder()
                .setId(1L)
                .setChannelId(1)
                .setText("Text of the first post")
                .setImages(List.of("img1", "img2"))
                .setCreatedDt(ZonedDateTime.now())
                .build());
    }

    @Override
    public long getPostsCount() {
        return 0;
    }
}
