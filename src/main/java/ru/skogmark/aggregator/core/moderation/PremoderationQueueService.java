package ru.skogmark.aggregator.core.moderation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nonnull;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class PremoderationQueueService {
    private static final Logger log = LoggerFactory.getLogger(PremoderationQueueService.class);

    private final TransactionTemplate transactionTemplate;
    private final PremoderationQueueDao premoderationQueueDao;

    public PremoderationQueueService(@Nonnull TransactionTemplate transactionTemplate,
                                     @Nonnull PremoderationQueueDao premoderationQueueDao) {
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.premoderationQueueDao = requireNonNull(premoderationQueueDao, "premoderationQueueDao");
    }

    public void enqueuePosts(@Nonnull List<UnmoderatedPost> posts) {
        requireNonNull(posts, "posts");
        log.info("enqueuePosts(): posts={}", posts);
        transactionTemplate.execute(status -> {
            // todo batch insert
            posts.forEach(premoderationQueueDao::insertPost);
            return null;
        });
    }
}
