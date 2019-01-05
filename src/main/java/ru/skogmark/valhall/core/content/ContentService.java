package ru.skogmark.valhall.core.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class ContentService {
    private static final Logger log = LoggerFactory.getLogger(ContentService.class);

    private final ParserFactory parserFactory;
    private final TransactionTemplate transactionTemplate;
    private final SourceDao sourceDao;

    public ContentService(@Nonnull ParserFactory parserFactory,
                          @Nonnull TransactionTemplate transactionTemplate,
                          @Nonnull SourceDao sourceDao) {
        this.parserFactory = requireNonNull(parserFactory, "parserFactory");
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.sourceDao = requireNonNull(sourceDao, "sourceDao");
    }

    public void parseContent(int sourceId, ContentListener contentListener) {
        log.info("parseContent(): sourceId={}", sourceId);
        Parser parser = parserFactory.getParser(sourceId);
        parser.auth(getAuthorizationMeta(sourceId).orElse(null),
                authorizationMeta -> {
                    insertOrUpdateAuthorizationMeta(authorizationMeta, sourceId);
                    long offset = getOffset(sourceId).orElse(0L);
                    parser.parse(offset).ifPresent(content -> {
                        log.info("Content obtained: sourceId={}, content={}", sourceId, content);
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
