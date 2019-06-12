package ru.skogmark.aggregator.core.topic;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import ru.skogmark.aggregator.AggregatorApplication;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

@Service
@Profile(AggregatorApplication.PROFILE_PRODUCTION)
public class TopicServiceImpl implements TopicService {
    private final TransactionTemplate transactionTemplate;
    private final TopicDao topicDao;

    public TopicServiceImpl(@Nonnull TransactionTemplate transactionTemplate, @Nonnull TopicDao topicDao) {
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.topicDao = requireNonNull(topicDao, "topicDao");
    }

    @Nonnull
    @Override
    public TopicPost addPost(@Nonnull TopicPost post) {
        return transactionTemplate.execute(status -> topicDao.insertPost(post));
    }
}
