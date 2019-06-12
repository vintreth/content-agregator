package ru.skogmark.aggregator.admin.moderation;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

class PostForm {
    private static final String FIELD_PUBLISH = "publish";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_TEXT = "text";
    private static final String FIELD_IMAGE = "image";
    private static final String FIELD_CHANNEL_ID = "channelId";

    private static final String VALUE_PUBLISH_ON = "on";

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

    @Nullable
    String getImage() {
        return form.get(FIELD_IMAGE);
    }

    @Nullable
    String getChannelId() {
        return form.get(FIELD_CHANNEL_ID);
    }
}
