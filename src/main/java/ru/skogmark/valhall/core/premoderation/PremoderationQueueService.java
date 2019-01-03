package ru.skogmark.valhall.core.premoderation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nonnull;

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

    public void enqueuePost(UnmoderatedPost post) {
        log.info("enqueuePost(): post={}", post);
        transactionTemplate.execute(transactionStatus -> {
            premoderationQueueDao.insertPost(post);
            return null;
        });
    }
}
