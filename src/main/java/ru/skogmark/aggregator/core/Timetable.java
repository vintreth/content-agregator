package ru.skogmark.aggregator.core;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class Timetable {
    private final Set<LocalTime> times;

    private Timetable(@Nonnull Set<LocalTime> times) {
        this.times = Set.copyOf(requireNonNull(times, "times"));
    }

    public static Timetable of(@Nonnull Set<LocalTime> times) {
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
