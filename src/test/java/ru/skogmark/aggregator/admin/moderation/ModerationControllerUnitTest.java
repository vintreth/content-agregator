package ru.skogmark.aggregator.admin.moderation;

import org.junit.Test;
import ru.skogmark.aggregator.core.PostImageSize;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModerationControllerUnitTest {
    @Test
    public void should_convert_postImage_to_image() {
        PostImageSize postImageSize = PostImageSize.builder()
                .setUuid(UUID.randomUUID().toString())
                .setSrc("https://sun9-34.userapi.com/c856128/v856128579/51413/G3-dcoxzNMY.jpg")
                .setWidth(640)
                .setHeight(480)
                .build();
        ImageSize imageSize = ModerationController.toImageSize(postImageSize);
        assertNotNull(imageSize);
        assertEquals(postImageSize.getSrc(), imageSize.getSrc());
        assertEquals("G3-dcoxzNMY.jpg [640x480]", imageSize.getTitle());
        assertEquals(postImageSize.getUuid(), imageSize.getUuid());
    }
}
