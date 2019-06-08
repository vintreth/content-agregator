package ru.skogmark.aggregator.admin.moderation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skogmark.aggregator.admin.Paginator;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueService;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping("/admin/moderation")
public class ModerationController {
    private static final String CATEGORY_MODERATION = "/moderation";
    private static final String VIEW_MODERATION_POSTS = CATEGORY_MODERATION + "/posts";

    private static final int DEFAULT_ON_PAGE_COUNT = 20;

    private final PremoderationQueueService premoderationQueueService;

    public ModerationController(@Nonnull PremoderationQueueService premoderationQueueService) {
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
    }

    @GetMapping("/posts/page/{page}")
    public String posts(Model model, @PathVariable("page") int page) {
        Paginator paginator = new Paginator(page, DEFAULT_ON_PAGE_COUNT, premoderationQueueService.getPostsCount());
        Paginator.OffsetInfo offsetInfo = paginator.getOffsetInfo();
        model.addAttribute("posts", premoderationQueueService.getPosts(
                offsetInfo.getLimit(), offsetInfo.getOffset()));
        model.addAttribute("page", page);
        model.addAttribute("pagesCount", paginator.getPagesCount());
        return VIEW_MODERATION_POSTS;
    }
}
