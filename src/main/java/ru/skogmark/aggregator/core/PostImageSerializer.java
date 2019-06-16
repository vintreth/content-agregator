package ru.skogmark.aggregator.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
public class PostImageSerializer {
    private static final Logger log = LoggerFactory.getLogger(PostImageSerializer.class);

    private final ObjectMapper objectMapper;

    public PostImageSerializer(@Nonnull ObjectMapper objectMapper) {
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    @Nullable
    public String serialize(@Nonnull List<PostImage> images) {
        requireNonNull(images, "images");
        try {
            if (images.isEmpty()) {
                return null;
            }
            return objectMapper.writeValueAsString(new ImagesWrapper(images));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to serialize object to json: object=" + images, e);
        }
    }

    @Nonnull
    public List<PostImage> deserialize(@Nonnull String images) {
        requireNonNull(images, "images");
        try {
            ImagesWrapper wrapper = objectMapper.readValue(images, ImagesWrapper.class);
            return wrapper.getImages();
        } catch (IOException e) {
            log.error("Unable to deserialize json to object: json={}", images, e);
            return Collections.emptyList();
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
