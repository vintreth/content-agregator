package ru.skogmark.aggregator.core.moderation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.skogmark.aggregator.core.PostImage;

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
                        .setImages(List.of(
                                PostImage.builder()
                                        .setSrc("img0")
                                        .setWidth(640)
                                        .setHeight(480)
                                        .build(),
                                PostImage.builder()
                                        .setSrc("img1")
                                        .setWidth(1024)
                                        .setHeight(768)
                                        .build()))
                        .setCreatedDt(now)
                        .setChangedDt(now)
                        .build(),
                UnmoderatedPost.builder()
                        .setId(2L)
                        .setChannelId(1)
                        .setText("Text of the second post")
                        .setImages(List.of(
                                PostImage.builder()
                                        .setSrc("img0")
                                        .setWidth(640)
                                        .setHeight(480)
                                        .build(),
                                PostImage.builder()
                                        .setSrc("img1")
                                        .setWidth(1024)
                                        .setHeight(768)
                                        .build()))
                        .setCreatedDt(now)
                        .setChangedDt(now)
                        .build(),
                UnmoderatedPost.builder()
                        .setId(3L)
                        .setChannelId(1)
                        .setText("Text of the third post")
                        .setImages(List.of(
                                PostImage.builder()
                                        .setSrc("img0")
                                        .setWidth(640)
                                        .setHeight(480)
                                        .build(),
                                PostImage.builder()
                                        .setSrc("img1")
                                        .setWidth(1024)
                                        .setHeight(768)
                                        .build()))
                        .setCreatedDt(now)
                        .setChangedDt(now)
                        .build());
    }

    @Override
    public void enqueuePosts(@Nonnull List<UnmoderatedPost> posts) {
        // doing nothing
    }

    @Override
    public Optional<UnmoderatedPost> getPost(long id) {
        ZonedDateTime now = ZonedDateTime.now();
        return Optional.of(UnmoderatedPost.builder()
                .setId(1L)
                .setChannelId(1)
                .setText("Text of the first post")
                .setImages(List.of(
                        PostImage.builder()
                                .setSrc("https://sun9-34.userapi.com/c856128/v856128579/51413/G3-dcoxzNMY.jpg")
                                .setWidth(130)
                                .setHeight(97)
                                .build(),
                        PostImage.builder()
                                .setSrc("https://sun9-24.userapi.com/c856128/v856128579/51418/g7SqbFXFpk4.jpg")
                                .setWidth(130)
                                .setHeight(98)
                                .build(),
                        PostImage.builder()
                                .setSrc("https://sun9-12.userapi.com/c856128/v856128579/51419/IHTU9-v-rEk.jpg")
                                .setWidth(200)
                                .setHeight(150)
                                .build(),
                        PostImage.builder()
                                .setSrc("https://sun9-21.userapi.com/c856128/v856128579/51415/LPqNVKREkCw.jpg")
                                .setWidth(807)
                                .setHeight(605)
                                .build()))
                .setCreatedDt(now)
                .setChangedDt(now)
                .build());
    }

    @Override
    public long getPostsCount() {
        return 0;
    }

    @Override
    public boolean updatePost(@Nonnull UnmoderatedPost post) {
        return true;
    }

    @Override
    public boolean publishPost(long id) {
        return true;
    }
}
