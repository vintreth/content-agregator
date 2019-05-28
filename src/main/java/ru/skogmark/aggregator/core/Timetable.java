package ru.skogmark.aggregator.core;

import javax.annotation.Nullable;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;

public class Timetable {
    private final Set<LocalTime> times;

    private Timetable(@Nullable Set<LocalTime> times) {
        this.times = nonNull(times) ? Collections.unmodifiableSet(new HashSet<>(times)) : Collections.emptySet();
    }

    public static Timetable of(@Nullable Set<LocalTime> times) {
        return new Timetable(times);
    }

    public Set<LocalTime> getTimes() {
        return times;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "times=" + times +
                '}';
    }
}
