package ru.skogmark.aggregator.client;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class QueryStringBuilder {
    private final String uri;
    private final Map<String, String> params = new HashMap<>();

    private QueryStringBuilder(@Nonnull String uri) {
        this.uri = requireNonNull(uri, "uri");
    }

    public static QueryStringBuilder of(@Nonnull String uri) {
        return new QueryStringBuilder(uri);
    }

    public QueryStringBuilder addParam(@Nonnull String paramName, int paramValue) {
        addParam(paramName, String.valueOf(paramValue));
        return this;
    }

    public QueryStringBuilder addParam(@Nonnull String paramName, long paramValue) {
        addParam(paramName, String.valueOf(paramValue));
        return this;
    }

    public QueryStringBuilder addParam(@Nonnull String paramName, @Nullable Object paramValue) {
        addParam(paramName, paramValue == null ? null : String.valueOf(paramValue));
        return this;
    }

    public QueryStringBuilder addParam(@Nonnull String paramName, @Nullable String paramValue) {
        requireNonNull(paramName, "paramName");
        if (paramValue != null && !paramValue.isEmpty()) {
            params.put(paramName, paramValue);
        }
        return this;
    }

    @Nonnull
    public String build() {
        String query = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        if (query != null && !query.isBlank()) {
            return uri + '?' + query;
        }
        return uri;
    }
}
