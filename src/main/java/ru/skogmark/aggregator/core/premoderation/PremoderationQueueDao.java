package ru.skogmark.aggregator.core.premoderation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

@Repository
class PremoderationQueueDao {
    private static final Logger log = LoggerFactory.getLogger(PremoderationQueueDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    PremoderationQueueDao(@Nonnull NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
    }

    @Nonnull
    UnmoderatedPost insertPost(@Nonnull UnmoderatedPost post) {
        requireNonNull(post, "post");
        log.info("insertPost(): post={}", post);
        long id = jdbcTemplate.queryForObject("insert into premoderation_queue (text, image_id, created_dt) " +
                        "values (:text, :imageId, :createdDt) " +
                        "returning id",
                new MapSqlParameterSource()
                        .addValue("text", post.getText().orElse(null))
                        .addValue("imageId", post.getImageId().orElse(null)),
                Long.class);
        log.info("Post inserted: id={}", id);
        return post.copy().setId(id).build();
    }
}
