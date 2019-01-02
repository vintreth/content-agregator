package ru.skogmark.valhall.core;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ContentService {
    @Nonnull
    public Optional<Content> getContent() {
        return Optional.of(new Content());
    }
}
