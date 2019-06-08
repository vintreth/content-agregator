package ru.skogmark.aggregator.core.moderation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skogmark.common.util.DateUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
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
        long id = jdbcTemplate.queryForObject("insert into premoderation_queue (channel_id, text, images) " +
                        "values (:channelId, :text, :images) " +
                        "returning id",
                new MapSqlParameterSource()
                        .addValue("channelId", post.getChannelId())
                        .addValue("text", post.getText())
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

    List<UnmoderatedPost> getPosts(int limit, long offset) {
        log.info("getPosts(): limit={}, offset={}", limit, offset);
        List<UnmoderatedPost> unmoderatedPosts = jdbcTemplate.query(
                "select id, channel_id, text, images, created_dt " +
                        "from premoderation_queue limit :limit offset :offset",
                new MapSqlParameterSource()
                        .addValue("limit", limit)
                        .addValue("offset", offset),
                (rs, rowNum) -> UnmoderatedPost.builder()
                        .setId(rs.getLong("id"))
                        .setChannelId(rs.getInt("channel_id"))
                        .setText(rs.getString("text"))
                        .setImages(getDeserializedValue(rs.getString("images")))
                        .setCreatedDt(DateUtils.toZonedDateTime(rs.getDate("created_dt")))
                        .build());
        log.info("Posts obtained: count={}", unmoderatedPosts.size());
        return unmoderatedPosts;
    }

    private List<String> getDeserializedValue(String images) {
        try {
            return objectMapper.readValue(images, List.class);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to deserialize json to object: json=" + images, e);
        }
    }

    long getPostsCount() {
        return jdbcTemplate.queryForObject("select count(id) from premoderation_queue",
                Collections.emptyMap(), Long.class);
    }
}
