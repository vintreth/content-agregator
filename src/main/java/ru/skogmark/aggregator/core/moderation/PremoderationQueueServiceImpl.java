package ru.skogmark.aggregator.core.moderation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

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

    public PremoderationQueueServiceImpl(@Nonnull TransactionTemplate transactionTemplate,
                                         @Nonnull PremoderationQueueDao premoderationQueueDao) {
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.premoderationQueueDao = requireNonNull(premoderationQueueDao, "premoderationQueueDao");
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
    public boolean updatePost(@Nonnull UnmoderatedPost unmoderatedPost) {
        return false;
    }
}
