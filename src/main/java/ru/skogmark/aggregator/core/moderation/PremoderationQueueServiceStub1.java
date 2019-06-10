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
                .setImages(List.of(
                        "https://sun9-34.userapi.com/c856128/v856128579/51413/G3-dcoxzNMY.jpg", 
                        "https://sun9-24.userapi.com/c856128/v856128579/51418/g7SqbFXFpk4.jpg",
                        "https://sun9-12.userapi.com/c856128/v856128579/51419/IHTU9-v-rEk.jpg",
                        "https://sun9-25.userapi.com/c856128/v856128579/5141a/eid6fUJh0no.jpg",
                        "https://sun9-4.userapi.com/c856128/v856128579/5141b/WTv8H9JNoEE.jpg",
                        "https://sun9-4.userapi.com/c856128/v856128579/51412/RE4OlADmy1w.jpg",
                        "https://sun9-30.userapi.com/c856128/v856128579/51417/vdTF_IM9ZZc.jpg",
                        "https://sun9-1.userapi.com/c856128/v856128579/51414/ObU6M2WIdUc.jpg",
                        "https://sun9-21.userapi.com/c856128/v856128579/51415/LPqNVKREkCw.jpg",
                        "https://sun9-18.userapi.com/c856128/v856128579/51416/zR-BieG1tRo.jpg"))
                .setCreatedDt(ZonedDateTime.now())
                .build());
    }

    @Override
    public long getPostsCount() {
        return 0;
    }
}
