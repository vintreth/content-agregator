package ru.skogmark.valhall.core.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;
import ru.skogmark.valhall.core.content.parser.Parser;
import ru.skogmark.valhall.core.content.parser.ParserFactory;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ContentService {
    private static final Logger log = LoggerFactory.getLogger(ContentService.class);

    private final Set<Source> sources;
    private final ParserFactory parserFactory;
    private final TransactionTemplate transactionTemplate;
    private final SourceDao sourceDao;

    public ContentService(@Nonnull Set<Source> sources,
                          @Nonnull ParserFactory parserFactory,
                          @Nonnull TransactionTemplate transactionTemplate,
                          @Nonnull SourceDao sourceDao) {
        this.sources = Collections.unmodifiableSet(new HashSet<>(requireNonNull(sources, "sources")));
        this.parserFactory = requireNonNull(parserFactory, "parserFactory");
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.sourceDao = requireNonNull(sourceDao, "sourceDao");
    }

    @Nonnull
    public List<Content> getContents() {
        log.info("Obtaining contents from all sources: sources={}", sources);
        return sources.stream()
                .map(this::getContent)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Content> getContent(Source source) {
        log.info("getContent(): source={}", source);
        Parser parser = parserFactory.getParser(source);
        parser.auth(getAuthorizationMeta(source.getId()).orElse(null),
                authorizationMeta -> {
                    insertOrUpdateAuthorizationMeta(authorizationMeta, source.getId());
                    long offset = getOffset(source.getId()).orElse(0L);
                    Optional<Content> content = parser.parse(offset);
                    
                });
        return Optional.empty();
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
