package ru.skogmark.aggregator.core.moderation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Repository
class PremoderationQueueDao {
    private static final Logger log = LoggerFactory.getLogger(PremoderationQueueDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    PremoderationQueueDao(@Nonnull NamedParameterJdbcTemplate jdbcTemplate, @Nonnull ObjectMapper objectMapper) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    @Nonnull
    UnmoderatedPost insertPost(@Nonnull UnmoderatedPost post) {
        requireNonNull(post, "post");
        log.info("insertPost(): post={}", post);
        long id = jdbcTemplate.queryForObject("insert into premoderation_queue (text, images) " +
                        "values (:text, :images) " +
                        "returning id",
                new MapSqlParameterSource()
                        .addValue("text", post.getText().orElse(null))
                        .addValue("images", getSerializedValue(post.getImages())),
                Long.class);
        log.info("Post inserted: id={}", id);
        return post.copy().setId(id).build();
    }

    @Nullable
    private String getSerializedValue(List<String> images) {
        try {
            if (images.isEmpty()) {
                return null;
            }
            return objectMapper.writeValueAsString(images);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to serialize object to json: object=" + images, e);
        }
    }
}
