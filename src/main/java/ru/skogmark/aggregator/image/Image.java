package ru.skogmark.aggregator.image;

import javax.annotation.Nullable;
import java.util.Optional;

// todo add interface and replace package
public class Image {
    @Nullable
    private final Long id;

    public Image(Long id) {
        this.id = id;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }
}
