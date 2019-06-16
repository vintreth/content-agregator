package ru.skogmark.aggregator.core.moderation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skogmark.aggregator.core.PostImageSerializer;
import ru.skogmark.common.util.DateUtils;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Repository
class PremoderationQueueDao {
    private static final String FIELDS = "id, channel_id, title, text, images, created_dt, changed_dt";

    private static final Logger log = LoggerFactory.getLogger(PremoderationQueueDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PostImageSerializer postImageSerializer;

    PremoderationQueueDao(@Nonnull NamedParameterJdbcTemplate jdbcTemplate,
                          @Nonnull PostImageSerializer postImageSerializer) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
        this.postImageSerializer = requireNonNull(postImageSerializer, "postImageSerializer");
    }

    @Nonnull
    UnmoderatedPost insertPost(@Nonnull UnmoderatedPost post) {
        requireNonNull(post, "post");
        log.info("insertPost(): post={}", post);
        long id = jdbcTemplate.queryForObject("insert into premoderation_queue (channel_id, title, text, images) " +
                        "values (:channelId, :title, :text, :images) " +
                        "returning id",
                new MapSqlParameterSource()
                        .addValue("channelId", post.getChannelId())
                        .addValue("title", post.getTitle().orElse(null))
                        .addValue("text", post.getText().orElse(null))
                        .addValue("images", postImageSerializer.serialize(post.getImages())),
                Long.class);
        log.info("Post inserted: id={}", id);
        return post.copy().setId(id).build();
    }

    // todo test
    Optional<UnmoderatedPost> getPost(long id) {
        log.info("getPost(): id={}", id);
        Optional<UnmoderatedPost> post = jdbcTemplate.query(
                "select " + FIELDS + " from premoderation_queue where id = :id",
                new MapSqlParameterSource()
                        .addValue("id", id),
                this::mapRow).stream().findFirst();
        log.info("Post obtained: post={}", post);
        return post;
    }

    List<UnmoderatedPost> getPosts(int limit, long offset) {
        log.info("getPosts(): limit={}, offset={}", limit, offset);
        List<UnmoderatedPost> unmoderatedPosts = jdbcTemplate.query(
                "select " + FIELDS + " from premoderation_queue order by id limit :limit offset :offset",
                new MapSqlParameterSource()
                        .addValue("limit", limit)
                        .addValue("offset", offset),
                this::mapRow);
        log.info("Posts obtained: count={}", unmoderatedPosts.size());
        return unmoderatedPosts;
    }

    long getPostsCount() {
        return jdbcTemplate.queryForObject("select count(id) from premoderation_queue",
                Collections.emptyMap(), Long.class);
    }

    // todo test
    boolean updatePost(@Nonnull UnmoderatedPost post) {
        requireNonNull(post, "post");
        log.info("updatePost(): post={}", post);
        boolean updated = jdbcTemplate.update("update premoderation_queue set" +
                        " channel_id = :channelId" +
                        ", title = :title" +
                        ", text = :text" +
                        ", images = :images" +
                        ", changed_dt = now()" +
                        " where id = :id",
                new MapSqlParameterSource()
                        .addValue("channelId", post.getChannelId())
                        .addValue("title", post.getTitle().orElse(null))
                        .addValue("text", post.getText().orElse(null))
                        .addValue("images", postImageSerializer.serialize(post.getImages()))
                        .addValue("id", post.getId().get())) > 0;
        if (updated) {
            log.info("Post updated: post={}", post);
        } else {
            log.info("Post not updated: post={}", post);
        }
        return updated;
    }

    boolean deletePost(long id) {
        log.info("deletePost(): id={}", id);
        boolean deleted = jdbcTemplate.update("delete from premoderation_queue where id = :id",
                new MapSqlParameterSource("id", id)) > 0;
        if (deleted) {
            log.info("Post deleted: id={}", id);
        } else {
            log.info("Post not deleted: id={}", id);
        }
        return deleted;
    }

    private UnmoderatedPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        String images = rs.getString("images");
        Timestamp changedDt = rs.getTimestamp("changed_dt");
        return UnmoderatedPost.builder()
                .setId(rs.getLong("id"))
                .setChannelId(rs.getInt("channel_id"))
                .setTitle(rs.getString("title"))
                .setText(rs.getString("text"))
                .setImages(images != null ? postImageSerializer.deserialize(images) : null)
                .setCreatedDt(DateUtils.toZonedDateTime(rs.getTimestamp("created_dt")))
                .setChangedDt(changedDt != null
                        ? DateUtils.toZonedDateTime(changedDt)
                        : null)
                .build();
    }
}
