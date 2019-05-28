package ru.skogmark.aggregator.core.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import ru.skogmark.aggregator.core.SourceContext;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class ContentService {
    private static final Logger log = LoggerFactory.getLogger(ContentService.class);

    private final TransactionTemplate transactionTemplate;
    private final SourceDao sourceDao;

    public ContentService(@Nonnull TransactionTemplate transactionTemplate,
                          @Nonnull SourceDao sourceDao) {
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.sourceDao = requireNonNull(sourceDao, "sourceDao");
    }

    public void parseContent(SourceContext sourceContext, ContentListener contentListener) {
        log.info("parseContent(): sourceId={}", sourceContext.getId());
        sourceContext.getParser().auth(getAuthorizationMeta(sourceContext.getId()).orElse(null),
                authorizationMeta -> {
                    insertOrUpdateAuthorizationMeta(authorizationMeta, sourceContext.getId());
                    long offset = getOffset(sourceContext.getId()).orElse(0L);
                    sourceContext.getParser().parse(offset).ifPresent(content -> {
                        log.info("Content obtained: sourceId={}, content={}", sourceContext.getId(), content);
                        contentListener.onContentParsed(content);
                    });
                });
    }

    private Optional<AuthorizationMeta> getAuthorizationMeta(int sourceId) {
        return sourceDao.getAuthorizationMeta(sourceId);
    }

    private void insertOrUpdateAuthorizationMeta(AuthorizationMeta authorizationMeta, int sourceId) {
        transactionTemplate.execute(status -> {
            Optional<AuthorizationMeta> storedAuthorizationMeta = getAuthorizationMeta(sourceId);
            if (!storedAuthorizationMeta.isPresent()) {
                log.info("AuthorizationMeta is absent, inserting...: authorizationMeta={}", authorizationMeta);
                sourceDao.insertAuthorizationMeta(authorizationMeta);
                return null;
            }
            if (!storedAuthorizationMeta.get().getAuthorizationToken().equals(authorizationMeta.getAuthorizationToken())) {
                log.info("AuthorizationMeta is obsolete, updating...: storedAuthorizationMeta={}, " +
                        "newAuthorizationMeta={}", storedAuthorizationMeta, authorizationMeta);
                sourceDao.updateAuthorizationMeta(authorizationMeta);
            }
            return null;
        });
    }

    private Optional<Long> getOffset(int sourceId) {
        return sourceDao.getOffset(sourceId);
    }
}
