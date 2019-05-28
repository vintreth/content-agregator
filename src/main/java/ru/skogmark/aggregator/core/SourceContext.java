package ru.skogmark.aggregator.core;

import ru.skogmark.aggregator.core.content.Parser;

import javax.annotation.Nonnull;

public interface SourceContext {
    int getId();

    @Nonnull
    Timetable getTimetable();

    @Nonnull
    Parser getParser();
}
