package ru.skogmark.valhall.core.premoderation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nonnull;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class PremoderationQueue {
    private static final Logger log = LoggerFactory.getLogger(PremoderationQueue.class);

    private final TransactionTemplate transactionTemplate;
    private final PremoderationQueueDao premoderationQueueDao;

    public PremoderationQueue(@Nonnull TransactionTemplate transactionTemplate,
                              @Nonnull PremoderationQueueDao premoderationQueueDao) {
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.premoderationQueueDao = requireNonNull(premoderationQueueDao, "premoderationQueueDao");
    }

    public void enqueuePosts(List<UnmoderatedPost> posts) {
        log.info("enqueuePosts(): posts={}", posts);
        transactionTemplate.execute(transactionStatus -> {
            posts.forEach(premoderationQueueDao::insertPost);
            return null;
        });
    }
}
