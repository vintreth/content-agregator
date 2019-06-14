package ru.skogmark.aggregator.core.moderation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.skogmark.aggregator.core.PostImage;
import ru.skogmark.common.util.DateUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
class UnmoderatedPostMapper implements RowMapper<UnmoderatedPost> {
    private final ObjectMapper objectMapper;

    UnmoderatedPostMapper(@Nonnull ObjectMapper objectMapper) {
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    @Override
    public UnmoderatedPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        String images = rs.getString("images");
        Timestamp changedDt = rs.getTimestamp("changed_dt");
        return UnmoderatedPost.builder()
                .setId(rs.getLong("id"))
                .setChannelId(rs.getInt("channel_id"))
                .setTitle(rs.getString("title"))
                .setText(rs.getString("text"))
                .setImages(images != null ? getDeserializedValue(images) : null)
                .setCreatedDt(DateUtils.toZonedDateTime(rs.getTimestamp("created_dt")))
                .setChangedDt(changedDt != null
                        ? DateUtils.toZonedDateTime(changedDt)
                        : null)
                .build();
    }

    @Nonnull
    String getSerializedValue(List<PostImage> images) {
        try {
            if (images.isEmpty()) {
                return null;
            }
            return objectMapper.writeValueAsString(new ImagesWrapper(images));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to serialize object to json: object=" + images, e);
        }
    }

    List<PostImage> getDeserializedValue(String images) {
        try {
            ImagesWrapper wrapper = objectMapper.readValue(images, ImagesWrapper.class);
            return wrapper.getImages();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to deserialize json to object: json=" + images, e);
        }
    }

    private static class ImagesWrapper {
        private final List<PostImage> images;

        @JsonCreator
        ImagesWrapper(@JsonProperty("images") List<PostImage> images) {
            this.images = images;
        }

        @JsonProperty("images")
        List<PostImage> getImages() {
            return images;
        }
    }
}