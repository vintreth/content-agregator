package ru.skogmark.aggregator.parser.vk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skogmark.aggregator.channel.Source;
import ru.skogmark.aggregator.core.content.Content;
import ru.skogmark.aggregator.core.content.ContentPost;
import ru.skogmark.aggregator.core.content.Parser;
import ru.skogmark.aggregator.core.content.ParsingContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
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
    public void parse(@Nonnull ParsingContext parsingContext) {
        requireNonNull(parsingContext, "parsingContext");
        log.info("Parsing content in vk: limit={}, offset={}",
                parsingContext.getLimit(), parsingContext.getOffset().orElse(null));
        Source source = Source.getById(parsingContext.getSourceId());
        vkApiClient.getWall(GetWallRequest.builder()
                        .setOwner(toOwner(source))
                        .setCount(parsingContext.getLimit())
                        .setOffset(parsingContext.getOffset().orElse(null))
                        .build(),
                result -> {
                    if (result.isError() || result.getResponse().isEmpty()) {
                        log.error("Vk api returned error result: result={}", result);
                    } else {
                        Content content = new Content(
                                result.getResponse().get().getItems().stream()
                                        .map(VkApiParser::toPost)
                                        .collect(Collectors.toList()),
                                calculateNextOffset(
                                        parsingContext.getOffset().orElse(null),
                                        parsingContext.getLimit(),
                                        result.getResponse().get().getCount()));
                        parsingContext.getOnContentReceivedCallback().accept(content);
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

    private static ContentPost toPost(Item item) {
        ContentPost.Builder builder = ContentPost.builder()
                .setExternalId(item.getId())
                .setText(item.getText().orElse(null));
        if (!item.getAttachments().isEmpty()) {
            getPhoto(item.getAttachments()).ifPresent(photo -> {
                List<String> images = photo.getSizes().stream()
                        .map(Size::getUrl)
                        .collect(Collectors.toList());
                builder.setImages(images);
            });
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

    private static long calculateNextOffset(@Nullable Long currentOffset,
                                            int limit,
                                            int totalMessagesCount) {
        if (currentOffset == null) {
            return totalMessagesCount - limit;
        } else if (currentOffset <= 0) {
            return 0;
        } else if (currentOffset - limit <= 0) {
            return 0;
        } else {
            return currentOffset - limit;
        }
    }
}
