package ru.skogmark.aggregator.core.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

@Repository
class TopicDao {
    private static final Logger log = LoggerFactory.getLogger(TopicDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    TopicDao(@Nonnull NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
    }

    @Nonnull
    TopicPost insertPost(@Nonnull TopicPost post) {
        requireNonNull(post, "post");
        log.info("insertPost(): post={}", post);
        Long id = jdbcTemplate.queryForObject("insert into topic (channel_id, title, text, images, active) values" +
                        " (:channelId, :title, :text, :images, :active) returning id",
                new MapSqlParameterSource()
                        .addValue("channelId", post.getChannelId()), // todo set values
                Long.class);
        return post.copy().setId(id).build();
    }
}
