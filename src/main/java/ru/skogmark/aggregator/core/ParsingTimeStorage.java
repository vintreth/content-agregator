package ru.skogmark.aggregator.core;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Component
class ParsingTimeStorage {
    private final Map<String, ZonedDateTime> storage = new HashMap<>();

    void put(int subjectId, int sourceId, ZonedDateTime parsingTime) {
        storage.put(buildKey(subjectId, sourceId), parsingTime);
    }

    boolean minuteExists(int subjectId, int sourceId, @Nonnull ZonedDateTime parsingTime) {
        requireNonNull(parsingTime, "parsingTime");
        ZonedDateTime storedTime = storage.get(buildKey(subjectId, sourceId));
        return nonNull(storedTime)
                && storedTime.getHour() == parsingTime.getHour()
                && storedTime.getMinute() == parsingTime.getMinute();
    }

    private static String buildKey(int subjectId, int sourceId) {
        return subjectId + "_" + sourceId;
    }
}
