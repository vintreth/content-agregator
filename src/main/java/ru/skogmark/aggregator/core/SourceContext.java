package ru.skogmark.aggregator.core;

import ru.skogmark.aggregator.core.content.Parser;

import javax.annotation.Nonnull;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class SourceContext {
    private final int sourceId;
    @Nonnull
    private final Timetable timetable;
    @Nonnull
    private final Parser parser;
    private final int parserLimit;

    private SourceContext(@Nonnull Integer sourceId,
                          @Nonnull Timetable timetable,
                          @Nonnull Parser parser,
                          @Nonnull Integer parserLimit) {
        this.sourceId = requireNonNull(sourceId, "id");
        this.timetable = requireNonNull(timetable, "timetable");
        this.parser = requireNonNull(parser, "parser");
        this.parserLimit = requireNonNull(parserLimit, "parserLimit");
    }

    @Override
    public String toString() {
        return "SourceContext{" +
                "sourceId=" + sourceId +
                ", timetable=" + timetable +
                ", parser=" + parser +
                ", parserLimit=" + parserLimit +
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
        return sourceId == ((SourceContext) o).sourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId);
    }

    public int getSourceId() {
        return sourceId;
    }

    @Nonnull
    public Timetable getTimetable() {
        return timetable;
    }

    @Nonnull
    public Parser getParser() {
        return parser;
    }

    public int getParserLimit() {
        return parserLimit;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Timetable timetable;
        private Parser parser;
        private Integer parserLimit;

        private Builder() {
        }

        public SourceContext build() {
            return new SourceContext(id, timetable, parser, parserLimit);
        }

        public Builder setSourceId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setTimetable(Timetable timetable) {
            this.timetable = timetable;
            return this;
        }

        public Builder setParser(Parser parser) {
            this.parser = parser;
            return this;
        }

        public Builder setParserLimit(Integer parserLimit) {
            this.parserLimit = parserLimit;
            return this;
        }
    }
}
