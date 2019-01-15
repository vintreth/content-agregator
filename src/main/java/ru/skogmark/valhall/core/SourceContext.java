package ru.skogmark.valhall.core;

import javax.annotation.Nonnull;

public interface SourceContext {
    int getId();

    @Nonnull
    Timetable getTimetable();
}
