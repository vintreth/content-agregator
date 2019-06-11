package ru.skogmark.aggregator.admin.moderation;

import org.junit.Test;
import ru.skogmark.aggregator.core.PostImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModerationControllerUnitTest {
    @Test
    public void should_convert_postImage_to_image() {
        PostImage postImage = PostImage.builder()
                .setSrc("https://sun9-34.userapi.com/c856128/v856128579/51413/G3-dcoxzNMY.jpg")
                .setWidth(640)
                .setHeight(480)
                .build();
        Image image = ModerationController.toImage(postImage);
        assertNotNull(image);
        assertEquals(postImage.getSrc(), image.getSrc());
        assertEquals("G3-dcoxzNMY.jpg [640x480]", image.getTitle());
    }
}
