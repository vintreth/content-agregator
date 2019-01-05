package ru.skogmark.valhall.core;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;

class Subject {
    private final int id;
    @Nonnull
    private final String name;
    @Nonnull
    private final Set<Source> sources;

    private Subject(@Nonnull Integer id, @Nonnull String name, @Nonnull Set<Source> sources) {
        this.id = requireNonNull(id, "id");
        this.name = requireNonNull(name, "name");
        this.sources = Collections.unmodifiableSet(new HashSet<>(requireNonNull(sources, "sources")));
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sources=" + sources +
                '}';
    }

    int getId() {
        return id;
    }

    @Nonnull
    String getName() {
        return name;
    }

    @Nonnull
    Set<Source> getSources() {
        return sources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return id == ((Subject) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private Integer id;
        private String name;
        private Set<Source> sources;

        private Builder() {
        }

        Subject build() {
            return new Subject(id, name, sources);
        }

        Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setSources(Set<Source> sources) {
            this.sources = sources;
            return this;
        }
    }
}
