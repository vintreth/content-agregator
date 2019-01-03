package ru.skogmark.valhall.core.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class SourceService {
    private static final Logger log = LoggerFactory.getLogger(SourceService.class);

    private final Source source;
    private final Parser parser;
    private final TransactionTemplate transactionTemplate;
    private final SourceDao sourceDao;

    public SourceService(@Nonnull Source source, @Nonnull Parser parser,
                         @Nonnull TransactionTemplate transactionTemplate, @Nonnull SourceDao sourceDao) {
        this.source = requireNonNull(source, "source");
        this.parser = requireNonNull(parser, "parser");
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.sourceDao = requireNonNull(sourceDao, "sourceDao");
    }

    public Optional<Content> getContent() {
        log.info("getContent(): source={}", source);
        parser.auth(authorizationMeta -> {
            insertOrUpdateAuthorizationMeta(authorizationMeta);

        });
        return Optional.empty();
    }

    private void insertOrUpdateAuthorizationMeta(AuthorizationMeta authorizationMeta) {
        transactionTemplate.execute(status -> {
            Optional<AuthorizationMeta> storedAuthorizationMeta = sourceDao.getAuthorizationMeta(source.getId());
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
}
