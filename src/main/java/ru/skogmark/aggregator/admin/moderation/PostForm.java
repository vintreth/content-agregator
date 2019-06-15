package ru.skogmark.aggregator.admin.moderation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class PostForm {
    private static final String FIELD_PUBLISH = "publish";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_TEXT = "text";
    private static final String FIELD_CHANNEL_ID = "channelId";

    private static final String VALUE_PUBLISH_ON = "on";
    private static final String VALUE_IMAGE_SIZE = "imageSize";

    private final Map<String, String> form;

    PostForm(@Nullable Map<String, String> form) {
        this.form = form != null ? Map.copyOf(form) : Collections.emptyMap();
    }

    @Nullable
    String getPublish() {
        return form.get(FIELD_PUBLISH);
    }

    boolean isPublish() {
        return VALUE_PUBLISH_ON.equals(getPublish());
    }

    @Nullable
    String getTitle() {
        return form.get(FIELD_TITLE);
    }

    @Nullable
    String getText() {
        return form.get(FIELD_TEXT);
    }

    @Nonnull
    List<String> getImageSizes() {
        return form.entrySet().stream()
                .filter(entry -> VALUE_IMAGE_SIZE.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Nullable
    String getChannelId() {
        return form.get(FIELD_CHANNEL_ID);
    }
}
