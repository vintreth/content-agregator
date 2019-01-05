package ru.skogmark.valhall.core;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubjectWorkerTest {
    @Test
    public void should_return_true_if_time_matched() {
        ZonedDateTime startingTime = ZonedDateTime.of(2019, 1, 1, 19, 0, 45, 0, ZoneId.systemDefault());
        Timetable timetable0 = Timetable.of(Sets.newHashSet(LocalTime.of(19, 0),
                LocalTime.of(7, 0)));
        Timetable timetable1 = Timetable.of(Sets.newHashSet(LocalTime.of(18, 0),
                LocalTime.of(19, 0)));
        assertTrue(SubjectWorker.isTimeMatched(startingTime, timetable0));
        assertTrue(SubjectWorker.isTimeMatched(startingTime, timetable1));
    }

    @Test
    public void should_return_false_if_time_not_matched() {
        ZonedDateTime startingTime0 = ZonedDateTime.of(2019, 1, 1, 19, 0, 45, 0, ZoneId.systemDefault());
        ZonedDateTime startingTime1 = ZonedDateTime.of(2019, 1, 1, 19, 1, 12, 0, ZoneId.systemDefault());
        Timetable timetable0 = Timetable.of(Sets.newHashSet(LocalTime.of(1, 0),
                LocalTime.of(13, 0)));
        Timetable timetable1 = Timetable.of(Sets.newHashSet(LocalTime.of(19, 0),
                LocalTime.of(0, 0)));
        assertFalse(SubjectWorker.isTimeMatched(startingTime0, timetable0));
        assertFalse(SubjectWorker.isTimeMatched(startingTime1, timetable1));
    }
}