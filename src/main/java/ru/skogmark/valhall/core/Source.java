package ru.skogmark.valhall.core;

import javax.annotation.Nonnull;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

class Source {
    private final int id;
    private final int parsingLimit;
    @Nonnull
    private final Timetable timetable;

    private Source(@Nonnull Integer id, @Nonnull Integer parsingLimit, @Nonnull Timetable timetable) {
        this.id = requireNonNull(id, "id");
        this.parsingLimit = requireNonNull(parsingLimit, "parsingLimit");
        this.timetable = requireNonNull(timetable, "timetable");
    }

    @Override
    public String toString() {
        return "Source{" +
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
        return id == ((Source) o).id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    int getId() {
        return id;
    }

    int getParsingLimit() {
        return parsingLimit;
    }

    @Nonnull
    Timetable getTimetable() {
        return timetable;
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private int id;
        private int parsingLimit;
        private Timetable timetable;

        private Builder() {
        }

        Source build() {
            return new Source(id, parsingLimit, timetable);
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setParsingLimit(int parsingLimit) {
            this.parsingLimit = parsingLimit;
            return this;
        }

        public Builder setTimetable(Timetable timetable) {
            this.timetable = timetable;
            return this;
        }
    }
}
