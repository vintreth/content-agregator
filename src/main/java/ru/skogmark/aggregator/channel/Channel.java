package ru.skogmark.aggregator.channel;

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
}
