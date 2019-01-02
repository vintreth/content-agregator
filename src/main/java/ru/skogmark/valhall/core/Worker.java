package ru.skogmark.valhall.core;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class Worker {
    private final ContentService contentService;

    public Worker(@Nonnull ContentService contentService) {
        this.contentService = Objects.requireNonNull(contentService, "contentService");
    }

    public void enqueuePost() {
        Optional<Content> content = contentService.getContent();

    }
}
