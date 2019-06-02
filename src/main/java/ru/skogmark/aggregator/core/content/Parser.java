package ru.skogmark.aggregator.core.content;

import java.util.List;
import java.util.function.Consumer;

public interface Parser {
    void parse(int sourceId, int limit, long offset, Consumer<List<Content>> onContentReceivedCallback);
}
