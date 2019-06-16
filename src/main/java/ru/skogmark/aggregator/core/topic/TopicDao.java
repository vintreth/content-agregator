package ru.skogmark.aggregator.core.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skogmark.aggregator.core.PostImageSerializer;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

@Repository
class TopicDao {
    private static final Logger log = LoggerFactory.getLogger(TopicDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PostImageSerializer postImageSerializer;

    TopicDao(@Nonnull NamedParameterJdbcTemplate jdbcTemplate,
             @Nonnull PostImageSerializer postImageSerializer) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
        this.postImageSerializer = requireNonNull(postImageSerializer, "postImageSerializer");
    }

    @Nonnull
    TopicPost insertPost(@Nonnull TopicPost post) {
        requireNonNull(post, "post");
        log.info("insertPost(): post={}", post);
        Long id = jdbcTemplate.queryForObject("insert into topic (channel_id, title, text, images, active) values" +
                        " (:channelId, :title, :text, :images, :active) returning id",
                new MapSqlParameterSource()
                        .addValue("channelId", post.getChannelId())
                        .addValue("title", post.getTitle().orElse(null))
                        .addValue("text", post.getText().orElse(null))
                        .addValue("images", postImageSerializer.serialize(post.getImages()))
                        .addValue("active", post.getActive() ? 1 : 0),
                Long.class);
        return post.copy().setId(id).build();
    }
}
