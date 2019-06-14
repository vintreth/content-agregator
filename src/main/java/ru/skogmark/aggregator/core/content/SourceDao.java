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
class SourceDao {
    private static final Logger log = LoggerFactory.getLogger(SourceDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    SourceDao(@Nonnull NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
    }

    Optional<Long> getOffset(int sourceId) {
        log.info("getOffset(): sourceId={}", sourceId);
        Optional<Long> offset = jdbcTemplate.queryForList(
                "select offset_value from source_offset where source_id = :sourceId",
                new MapSqlParameterSource()
                        .addValue("sourceId", sourceId),
                Long.class).stream().findFirst();
        log.info("Offset obtained: sourceId={}, offset={}", sourceId, offset);
        return offset;
    }

    boolean updateOffset(int sourceId, long offsetValue) {
        log.info("updateOffset(): sourceId={}, offsetValue={}", sourceId, offsetValue);
        return jdbcTemplate.update(
                "update source_offset set offset_value = :offsetValue where source_id = :sourceId",
                new MapSqlParameterSource()
                        .addValue("sourceId", sourceId)
                        .addValue("offsetValue", offsetValue)) > 0;
    }
}
