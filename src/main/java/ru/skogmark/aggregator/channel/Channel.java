package ru.skogmark.aggregator.channel;

import java.util.Arrays;

public enum Channel {
    MEMES(1, "Memes");

    private final int id;
    private final String name;

    Channel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Channel getById(int id) {
        return Arrays.stream(values())
                .filter(channel -> channel.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No channel with such id exists: id=" + id));
    }
}
