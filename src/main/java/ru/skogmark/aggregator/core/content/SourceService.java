package ru.skogmark.aggregator.core.content;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class SourceService {
    private final SourceDao sourceDao;
    private final TransactionTemplate transactionTemplate;

    public SourceService(@Nonnull SourceDao sourceDao, @Nonnull TransactionTemplate transactionTemplate) {
        this.sourceDao = requireNonNull(sourceDao, "sourceDao");
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
    }

    public Optional<Long> getOffset(int sourceId) {
        return sourceDao.getOffset(sourceId);
    }

    public boolean upsertOffset(int sourceId, long offsetValue) {
        return transactionTemplate.execute(status -> sourceDao.upsertOffset(sourceId, offsetValue));
    }
}
