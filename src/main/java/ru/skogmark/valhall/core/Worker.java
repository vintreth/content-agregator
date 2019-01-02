package ru.skogmark.valhall.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skogmark.valhall.core.content.Content;
import ru.skogmark.valhall.core.content.ContentService;
import ru.skogmark.valhall.core.premoderation.PremoderationQueue;
import ru.skogmark.valhall.core.premoderation.UnmoderatedPost;
import ru.skogmark.valhall.image.Image;
import ru.skogmark.valhall.image.ImageDownloadService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Worker {
    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    private final String name;
    private final ContentService contentService;
    private final PremoderationQueue premoderationQueue;
    private final ImageDownloadService imageDownloadService;

    public Worker(@Nonnull String name, @Nonnull ContentService contentService,
                  @Nonnull PremoderationQueue premoderationQueue, @Nonnull ImageDownloadService imageDownloadService) {
        this.name = requireNonNull(name, "name");
        this.contentService = requireNonNull(contentService, "contentService");
        this.premoderationQueue = requireNonNull(premoderationQueue, "premoderationQueue");
        this.imageDownloadService = requireNonNull(imageDownloadService, "imageDownloadService");
    }

    public void doWork() {
        log.info("Starting to obtain contents and enqueueing the posts: workerName={}", name);
        List<Content> contents = contentService.getContents();
        if (contents.isEmpty()) {
            log.info("No contents found. Skipping...: workerName={}", name);
            return;
        }
        log.info("Contents obtained successfully: workerName={}", name);
        premoderationQueue.enqueuePosts(contents.stream()
                .map(content -> {
                    Optional<Image> image = imageDownloadService.downloadAndSave(content.getImageUri());
                    return buildUnmoderatedPost(content.getText(), image.flatMap(Image::getId).orElse(null));
                })
                .collect(Collectors.toList()));
    }

    private static UnmoderatedPost buildUnmoderatedPost(@Nonnull String text, @Nullable Long imageId) {
        requireNonNull(text, "text");
        return UnmoderatedPost.builder()
                .text(text)
                .imageId(imageId)
                .build();
    }
}
