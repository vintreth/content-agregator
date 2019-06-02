package ru.skogmark.aggregator.channel;

import javax.annotation.Nonnull;
import java.util.Arrays;

public enum Source {
    VK_LEPRA(1);

    private int id;

    Source(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public static Source getById(int id) {
        return Arrays.stream(values())
                .filter(source -> source.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown source with id: id=" + id));
    }
}
