package ru.skogmark.valhall.image;

import javax.annotation.Nullable;
import java.util.Optional;

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
