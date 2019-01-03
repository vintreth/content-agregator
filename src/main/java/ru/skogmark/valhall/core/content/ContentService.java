package ru.skogmark.valhall.core.content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ContentService {
    private static final Logger log = LoggerFactory.getLogger(ContentService.class);

    private final String name;
    private final List<SourceService> sourceServices;

    public ContentService(@Nonnull String name, @Nonnull List<SourceService> sourceServices) {
        this.name = requireNonNull(name, "name");
        this.sourceServices = Collections.unmodifiableList(new ArrayList<>(requireNonNull(sourceServices,
                "sourceServices")));
    }

    @Nonnull
    public List<Content> getContents() {
        log.info("Obtaining contents from all sources: name={}", name);
        return sourceServices.stream()
                .map(SourceService::getContent)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
