package ru.skogmark.aggregator.parser.vk;

enum Owner {
    LEPRA(-30022666);

    private long id;

    Owner(long id) {
        this.id = id;
    }

    long getId() {
        return id;
    }
}
