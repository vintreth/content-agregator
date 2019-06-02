package ru.skogmark.aggregator.parser.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skogmark.aggregator.channel.Source;
import ru.skogmark.aggregator.core.content.Content;
import ru.skogmark.aggregator.core.content.Parser;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class VkApiParser implements Parser {
    private static final Logger log = LoggerFactory.getLogger(VkApiParser.class);

    private final VkApiClient vkApiClient;

    public VkApiParser(@Nonnull VkApiClient vkApiClient) {
        this.vkApiClient = requireNonNull(vkApiClient, "vkApiClient");
    }

    @Override
    public void parse(int sourceId, int limit, long offset, Consumer<List<Content>> onContentReceivedCallback) {
        log.info("Starting parsing content in vk: limit={}, offset={}", limit, offset);
        Source source = Source.getById(sourceId);
        vkApiClient.getWall(GetWallRequest.builder()
                        .withOwner(toOwner(source))
                        .withCount(limit)
                        .withOffset(offset)
                        .build(),
                result -> {
                    if (result.isError() || result.getResponse().isEmpty()) {
                        log.error("Vk api returned error result: result={}", result);
                    } else {
                        onContentReceivedCallback.accept(result.getResponse().get().getItems().stream()
                                .map(VkApiParser::toContent)
                                .collect(Collectors.toList()));
                    }
                });
    }

    private static Owner toOwner(Source source) {
        switch (source) {
            case VK_LEPRA:
                return Owner.LEPRA;
            default:
                throw new IllegalArgumentException("Unknown source: source=" + source);
        }
    }

    private static Content toContent(Item item) {
        Content.Builder builder = Content.builder()
                .setExternalId(item.getId())
                .setText(item.getText());
        if (!item.getAttachments().isEmpty()) {
            Optional<Photo> photo = getPhoto(item.getAttachments());
            photo.ifPresent(p -> p.getSizes().stream()
                    .filter(size -> "w".equals(size.getType()))
                    .map(Size::getUrl)
                    .findFirst()
                    .ifPresent(url -> {
                        try {
                            builder.setImageUri(new URI(url));
                        } catch (URISyntaxException e) {
                            log.error("Image url has invalid format: url=" + url, e);
                        }
                    }));
        }
        return builder.build();
    }

    private static Optional<Photo> getPhoto(List<Attachment> attachments) {
        return attachments.stream()
                .filter(attachment -> "photo".equals(attachment.getType()))
                .filter(attachment -> attachment.getPhoto().isPresent())
                .map(attachment -> attachment.getPhoto().get())
                .findFirst();
    }
}
