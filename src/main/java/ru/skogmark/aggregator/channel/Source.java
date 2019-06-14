package ru.skogmark.aggregator.channel;

import javax.annotation.Nonnull;
import java.util.Arrays;

public enum Source {
    VK_LEPRA(1, "ВКонтакте Лепра");

    private final int id;
    private final String description;

    Source(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Nonnull
    public static Source getById(int id) {
        return Arrays.stream(values())
                .filter(source -> source.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown source with id: id=" + id));
    }
}
