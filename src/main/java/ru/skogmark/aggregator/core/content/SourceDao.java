package ru.skogmark.aggregator.core.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Repository
public class SourceDao {
    private static final Logger log = LoggerFactory.getLogger(SourceDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SourceDao(@Nonnull NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
    }

    public void insertAuthorizationMeta(@Nonnull AuthorizationMeta authorizationMeta) {
        requireNonNull(authorizationMeta, "authorizationMeta");
        log.info("insertAuthorizationMeta(): authorizationMeta={}", authorizationMeta);
        jdbcTemplate.update("insert into authorization_meta (source_id, authorization_token) values " +
                        "(:sourceId, :authorizationToken)",
                new MapSqlParameterSource()
                        .addValue("sourceId", authorizationMeta.getSourceId())
                        .addValue("authorizationToken", authorizationMeta.getAuthorizationToken()));
        log.info("AuthorizationMeta inserted: authorizationMeta={}", authorizationMeta);
    }

    public Optional<AuthorizationMeta> getAuthorizationMeta(int sourceId) {
        log.info("getAuthorizationMeta(): sourceId={}", sourceId);
        AuthorizationMeta authorizationMeta = jdbcTemplate.queryForObject(
                "select source_id, authorization_token from authorization_meta where source_id = :sourceId",
                new MapSqlParameterSource()
                        .addValue("sourceId", sourceId),
                (ps, rowNum) -> {
                    int storedSourceId = ps.getInt("source_id");
                    return AuthorizationMeta.of(storedSourceId, ps.getString("authorization_token"));
                });
        log.info("AuthorizationMeta obtained: sourceId={}, authorizationMeta={}", sourceId, authorizationMeta);
        return Optional.ofNullable(authorizationMeta);
    }

    public void updateAuthorizationMeta(@Nonnull AuthorizationMeta authorizationMeta) {
        log.info("updateAuthorizationMeta(): authorizationMeta={}", authorizationMeta);
        int affectedRows = jdbcTemplate.update(
                "update authorization_meta set authorization_token = :authorizationToken " +
                        "where source_id = :sourceId",
                new MapSqlParameterSource()
                        .addValue("authorizationToken", authorizationMeta.getAuthorizationToken())
                        .addValue("sourceId", authorizationMeta.getSourceId()));
        log.info("AuthorizationMeta updated: affectedRows={}, authorizationMeta={}", affectedRows, authorizationMeta);
    }

    public Optional<Long> getOffset(int sourceId) {
        log.info("getOffset(): sourceId={}", sourceId);
        Long offset = jdbcTemplate.queryForObject(
                "select offset_value from source_offset where source_id = :sourceId",
                new MapSqlParameterSource()
                        .addValue("sourceId", sourceId),
                Long.class);
        log.info("Offset obtained: sourceId={}, offset={}", sourceId, offset);
        return Optional.ofNullable(offset);
    }
}
