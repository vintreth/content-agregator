package ru.skogmark.aggregator.admin.moderation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skogmark.aggregator.admin.Paginator;
import ru.skogmark.aggregator.channel.Channel;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueService;
import ru.skogmark.aggregator.core.moderation.UnmoderatedPost;

import javax.annotation.Nonnull;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping("/admin/moderation")
public class ModerationController {
    private static final String CATEGORY_MODERATION = "/admin/moderation";
    private static final String VIEW_MODERATION_POSTS = CATEGORY_MODERATION + "/posts";

    private static final int DEFAULT_ON_PAGE_COUNT = 20;

    private static final DateTimeFormatter viewTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd MMM");

    private final PremoderationQueueService premoderationQueueService;

    public ModerationController(@Nonnull PremoderationQueueService premoderationQueueService) {
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
    }

    @GetMapping("/posts/page/{page}")
    public String posts(Model model, @PathVariable("page") int page) {
        Paginator paginator = new Paginator(page, DEFAULT_ON_PAGE_COUNT, premoderationQueueService.getPostsCount());
        Paginator.OffsetInfo offsetInfo = paginator.getOffsetInfo();
        List<Post> viewPosts = premoderationQueueService.getPosts(offsetInfo.getLimit(), offsetInfo.getOffset())
                .stream()
                .map(ModerationController::toPost)
                .collect(Collectors.toList());

        model.addAttribute("posts", viewPosts);
        model.addAttribute("page", page);
        model.addAttribute("pagesCount", paginator.getPagesCount());
        return VIEW_MODERATION_POSTS;
    }

    private static Post toPost(UnmoderatedPost unmoderatedPost) {
        return Post.builder()
                .setId(unmoderatedPost.getId().orElse(null))
                .setChannel(Channel.getById(unmoderatedPost.getChannelId()).getName())
                .setText(unmoderatedPost.getText())
                .setImages(unmoderatedPost.getImages())
                .setCreatedDt(unmoderatedPost.getCreatedDt().map(viewTimeFormatter::format)
                        .orElse(null))
                .build();
    }
}
