package ru.skogmark.valhall.source;

import ru.skogmark.valhall.core.SourceContext;
import ru.skogmark.valhall.core.Timetable;

import javax.annotation.Nonnull;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class SourceContextImpl implements SourceContext {
    private final int id;
    private final int parsingLimit;
    @Nonnull
    private final Timetable timetable;

    private SourceContextImpl(@Nonnull Integer id, @Nonnull Integer parsingLimit, @Nonnull Timetable timetable) {
        this.id = requireNonNull(id, "id");
        this.parsingLimit = requireNonNull(parsingLimit, "parsingLimit");
        this.timetable = requireNonNull(timetable, "timetable");
    }

    @Override
    public String toString() {
        return "SourceContext{" +
                "id=" + id +
                ", parsingLimit=" + parsingLimit +
                ", timetable=" + timetable +
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private int parsingLimit;
        private Timetable timetable;

        private Builder() {
        }

        public SourceContext build() {
            return new SourceContextImpl(id, parsingLimit, timetable);
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
    }
}
