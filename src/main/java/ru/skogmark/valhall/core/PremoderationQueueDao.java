package ru.skogmark.valhall.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

@Repository
public class PremoderationQueueDao {
    private static final Logger log = LoggerFactory.getLogger(PremoderationQueueDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PremoderationQueueDao(@Nonnull NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
    }

    @Nonnull
    public UnmoderatedPost insertPost(@Nonnull UnmoderatedPost post) {
        requireNonNull(post, "post");
        log.info("insertPost(): post={}", post);
        long id = jdbcTemplate.queryForObject("insert into premoderation_queue (text, image_id, created_dt) " +
                        "values (:text, :imageId, :createdDt) " +
                        "returning id",
                new MapSqlParameterSource()
                        .addValue("text", post.getText().orElse(null))
                        .addValue("imageId", post.getImageId().orElse(null)),
                Long.class);
        return post.copy().setId(id).build();
    }
}
