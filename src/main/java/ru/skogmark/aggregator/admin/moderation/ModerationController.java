package ru.skogmark.aggregator.admin.moderation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.skogmark.aggregator.admin.AdminController;
import ru.skogmark.aggregator.admin.Paginator;
import ru.skogmark.aggregator.channel.Channel;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueService;
import ru.skogmark.aggregator.core.moderation.UnmoderatedPost;

import javax.annotation.Nonnull;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping("/admin/moderation")
public class ModerationController {
    static final DateTimeFormatter viewTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd MMM");

    private static final String CATEGORY_MODERATION = "admin/moderation";
    private static final String VIEW_MODERATION_POSTS = CATEGORY_MODERATION + "/posts";
    private static final String VIEW_MODERATION_POST = CATEGORY_MODERATION + "/post";

    private static final int DEFAULT_ON_PAGE_COUNT = 20;

    private static final Logger log = LoggerFactory.getLogger(ModerationController.class);

    private final PremoderationQueueService premoderationQueueService;
    private final AdminController adminController;

    public ModerationController(@Nonnull PremoderationQueueService premoderationQueueService,
                                @Nonnull AdminController adminController) {
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
        this.adminController = requireNonNull(adminController, "adminController");
    }

    @GetMapping("/posts/page/{page}/")
    public String getPosts(Model model, @PathVariable("page") int page) {
        log.info("posts(): page={}", page);
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

    @GetMapping("/posts/{id}/")
    public String getPost(Model model, @PathVariable("id") long id) {
        log.info("post(): id={}", id);
        Optional<UnmoderatedPost> unmoderatedPost = premoderationQueueService.getPost(id);
        if (unmoderatedPost.isEmpty()) {
            log.error("Post not found: id={}", id);
            return adminController.error404();
        }

        Post post = toPost(unmoderatedPost.get());
        model.addAttribute("post", post);
        model.addAttribute("channels", Arrays.stream(Channel.values())
                .filter(channel -> channel.getId() != post.getChannelId())
                .collect(Collectors.toList()));
        return VIEW_MODERATION_POST;
    }

    @PostMapping(value = "/posts/{id}/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String savePost(Model model, @PathVariable("id") long id, @RequestParam Map<String, String> form) {
        log.info("form={}", form);
        return VIEW_MODERATION_POST;
    }

    private static Post toPost(UnmoderatedPost unmoderatedPost) {
        Channel channel = Channel.getById(unmoderatedPost.getChannelId());
        return Post.builder()
                .setId(unmoderatedPost.getId().orElse(null))
                .setChannel(channel.getName())
                .setChannelId(channel.getId())
                // todo set title
                .setText(unmoderatedPost.getText().orElse(null))
                .setImages(unmoderatedPost.getImages())
                .setCreatedDt(unmoderatedPost.getCreatedDt().map(viewTimeFormatter::format)
                        .orElse(null))
                .build();
    }
}
