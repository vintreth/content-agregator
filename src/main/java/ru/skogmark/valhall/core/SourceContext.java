package ru.skogmark.valhall.core;

import ru.skogmark.valhall.core.content.Parser;

import javax.annotation.Nonnull;

public interface SourceContext {
    int getId();

    @Nonnull
    Timetable getTimetable();

    @Nonnull
    Parser getParser();
}
