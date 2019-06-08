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

    void put(int channelId, int sourceId, ZonedDateTime parsingTime) {
        storage.put(buildKey(channelId, sourceId), parsingTime);
    }

    boolean minuteExists(int channelId, int sourceId, @Nonnull ZonedDateTime parsingTime) {
        requireNonNull(parsingTime, "parsingTime");
        ZonedDateTime storedTime = storage.get(buildKey(channelId, sourceId));
        return nonNull(storedTime)
                && storedTime.getHour() == parsingTime.getHour()
                && storedTime.getMinute() == parsingTime.getMinute();
    }

    private static String buildKey(int channelId, int sourceId) {
        return channelId + "_" + sourceId;
    }
}
