package ru.skogmark.aggregator.core.moderation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import ru.skogmark.aggregator.core.topic.TopicPost;
import ru.skogmark.aggregator.core.topic.TopicService;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static ru.skogmark.aggregator.AggregatorApplication.PROFILE_PRODUCTION;

@Service
@Profile(PROFILE_PRODUCTION)
public class PremoderationQueueServiceImpl implements PremoderationQueueService {
    private static final Logger log = LoggerFactory.getLogger(PremoderationQueueServiceImpl.class);

    private final TransactionTemplate transactionTemplate;
    private final PremoderationQueueDao premoderationQueueDao;
    private final TopicService topicService;

    public PremoderationQueueServiceImpl(@Nonnull TransactionTemplate transactionTemplate,
                                         @Nonnull PremoderationQueueDao premoderationQueueDao,
                                         @Nonnull TopicService topicService) {
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.premoderationQueueDao = requireNonNull(premoderationQueueDao, "premoderationQueueDao");
        this.topicService = requireNonNull(topicService, "topicService");
    }

    @Override
    public Optional<UnmoderatedPost> getPost(long id) {
        return premoderationQueueDao.getPost(id);
    }

    @Override
    public List<UnmoderatedPost> getPosts(int limit, long offset) {
        return premoderationQueueDao.getPosts(limit, offset);
    }

    @Override
    public long getPostsCount() {
        return premoderationQueueDao.getPostsCount();
    }

    @Override
    public void enqueuePosts(@Nonnull List<UnmoderatedPost> posts) {
        requireNonNull(posts, "posts");
        log.info("enqueuePosts(): posts={}", posts);
        transactionTemplate.execute(status -> {
            // todo batch insert
            posts.forEach(premoderationQueueDao::insertPost);
            return null;
        });
    }

    @Override
    public boolean updatePost(@Nonnull UnmoderatedPost post) {
        return transactionTemplate.execute(status -> premoderationQueueDao.updatePost(post));
    }

    @Override
    public boolean publishPost(long id) {
        // todo move to async task
        log.info("publishPost(): id={}", id);
        Optional<UnmoderatedPost> unmoderatedPost = getPost(id);
        if (unmoderatedPost.isEmpty()) {
            log.error("Post not found: id={}", id);
            return false;
        }
        return transactionTemplate.execute(status -> {
            TopicPost publishedPost = topicService.addPost(toTopicPost(unmoderatedPost.get()));
            if (!deletePost(id)) {
                log.error("Unable to delete unmoderatedPost: id={}", id);
                return false;
            }
            log.info("Post published: post={}", publishedPost);
            return true;
        });
    }

    private boolean deletePost(long id) {
        return transactionTemplate.execute(status -> premoderationQueueDao.deletePost(id));
    }

    private static TopicPost toTopicPost(UnmoderatedPost unmoderatedPost) {
        return TopicPost.builder()
                .setChannelId(unmoderatedPost.getChannelId())
                .setTitle(unmoderatedPost.getTitle().orElse(null))
                .setText(unmoderatedPost.getText().orElse(null))
                .setImages(unmoderatedPost.getImages())
                .setActive(true)
                .build();
    }
}
