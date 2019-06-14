package ru.skogmark.aggregator.channel;

import java.util.Arrays;

public enum Channel {
    MEMES(1, "Memes", "Телеграмм канал - мемчики"),
    CHANNEL_2(2, "Channel 2", "Какой-то еще канал"),
    CHANNEL_3(3, "Channel 3", "Хз для теста");

    private final int id;
    private final String name;
    private final String description;

    Channel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static Channel getById(int id) {
        return Arrays.stream(values())
                .filter(channel -> channel.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No channel with such id exists: id=" + id));
    }
}
