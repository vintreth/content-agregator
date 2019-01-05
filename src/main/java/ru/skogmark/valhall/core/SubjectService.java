package ru.skogmark.valhall.core;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

class SubjectService {
    List<Subject> getSubjects() {
        return Collections.emptyList(); // todo
    }

    @Nonnull
    Timetable getTimetable(int sourceId) {
        // todo
        return Timetable.of(null);
    }
}
