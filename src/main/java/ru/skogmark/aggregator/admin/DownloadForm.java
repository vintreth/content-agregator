package ru.skogmark.aggregator.admin;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

class DownloadForm {
    private final static String FIELD_SOURCE_ID = "sourceId";
    private final static String FIELD_CHANNEL_ID = "channelId";
    private final static String FIELD_LIMIT = "limit";

    private final Map<String, String> form;

    DownloadForm(@Nullable Map<String, String> form) {
        this.form = form != null ? Map.copyOf(form) : Collections.emptyMap();
    }

    @Nullable
    String getSourceId() {
        return form.get(FIELD_SOURCE_ID);
    }

    @Nullable
    String getChannelId() {
        return form.get(FIELD_CHANNEL_ID);
    }

    @Nullable
    String getLimit() {
        return form.get(FIELD_LIMIT);
    }
}
