package ru.skogmark.valhall.core;

import ru.skogmark.valhall.core.content.Source;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class Subject {
    @Nonnull
    private final String name;

    @Nonnull
    private final Set<Source> sources;

    private Subject(@Nonnull String name, @Nonnull Set<Source> sources) {
        this.name = requireNonNull(name, "name");
        this.sources = Collections.unmodifiableSet(new HashSet<>(requireNonNull(sources, "sources")));
    }

    public static Subject of(@Nonnull String name, @Nonnull Set<Source> sources) {
        return new Subject(name, sources);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", sources=" + sources +
                '}';
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Set<Source> getSources() {
        return sources;
    }
}
