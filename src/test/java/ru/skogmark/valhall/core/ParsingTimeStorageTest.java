package ru.skogmark.valhall.core;

import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParsingTimeStorageTest {
    @Test
    public void should_return_true_if_minute_exists() {
        ZonedDateTime parsedTime = ZonedDateTime.of(2019, 1, 1, 21, 0, 0, 0, ZoneId.systemDefault());
        ParsingTimeStorage storage = new ParsingTimeStorage();
        storage.put(1, 1, parsedTime);

        assertTrue(storage.minuteExists(1, 1, ZonedDateTime.of(2019, 1, 1, 21, 0, 10, 0, ZoneId.systemDefault())));
        assertTrue(storage.minuteExists(1, 1, ZonedDateTime.of(2019, 1, 1, 21, 0, 59, 0, ZoneId.systemDefault())));
        assertFalse(storage.minuteExists(1, 1, ZonedDateTime.of(2019, 1, 1, 21, 1, 1, 0, ZoneId.systemDefault())));
        assertFalse(storage.minuteExists(1, 1, ZonedDateTime.of(2019, 1, 1, 22, 0, 0, 0, ZoneId.systemDefault())));

        parsedTime = ZonedDateTime.of(2019, 1, 1, 21, 0, 5, 0, ZoneId.systemDefault());
        storage = new ParsingTimeStorage();
        storage.put(1, 1, parsedTime);

        assertTrue(storage.minuteExists(1, 1, ZonedDateTime.of(2019, 1, 1, 21, 0, 10, 0, ZoneId.systemDefault())));
        assertTrue(storage.minuteExists(1, 1, ZonedDateTime.of(2019, 1, 1, 21, 0, 59, 0, ZoneId.systemDefault())));
        assertFalse(storage.minuteExists(1, 1, ZonedDateTime.of(2019, 1, 1, 21, 1, 1, 0, ZoneId.systemDefault())));
        assertFalse(storage.minuteExists(1, 1, ZonedDateTime.of(2019, 1, 1, 22, 0, 0, 0, ZoneId.systemDefault())));
    }
}