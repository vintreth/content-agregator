package ru.skogmark.aggregator.source;

import ru.skogmark.aggregator.core.SourceContext;
import ru.skogmark.aggregator.core.Timetable;
import ru.skogmark.aggregator.core.content.Parser;

import javax.annotation.Nonnull;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class SourceContextImpl implements SourceContext {
    private final int id;
    private final int parsingLimit;
    @Nonnull
    private final Timetable timetable;
    @Nonnull
    private final Parser parser;

    private SourceContextImpl(@Nonnull Integer id,
                              @Nonnull Integer parsingLimit,
                              @Nonnull Timetable timetable,
                              @Nonnull Parser parser) {
        this.id = requireNonNull(id, "id");
        this.parsingLimit = requireNonNull(parsingLimit, "parsingLimit");
        this.timetable = requireNonNull(timetable, "timetable");
        this.parser = requireNonNull(parser, "parser");
    }

    @Override
    public String toString() {
        return "SourceContext{" +
                "id=" + id +
                ", parsingLimit=" + parsingLimit +
                ", timetable=" + timetable +
                ", parser=" + parser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((SourceContextImpl) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public int getParsingLimit() {
        return parsingLimit;
    }

    @Nonnull
    public Timetable getTimetable() {
        return timetable;
    }

    @Nonnull
    @Override
    public Parser getParser() {
        return parser;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private int parsingLimit;
        private Timetable timetable;
        private Parser parser;

        private Builder() {
        }

        public SourceContext build() {
            return new SourceContextImpl(id, parsingLimit, timetable, parser);
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder parsingLimit(int parsingLimit) {
            this.parsingLimit = parsingLimit;
            return this;
        }

        public Builder timetable(Timetable timetable) {
            this.timetable = timetable;
            return this;
        }

        public Builder parser(Parser parser) {
            this.parser = parser;
            return this;
        }
    }
}
