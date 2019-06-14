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
import ru.skogmark.aggregator.core.PostImage;
import ru.skogmark.aggregator.core.moderation.PremoderationQueueService;
import ru.skogmark.aggregator.core.moderation.UnmoderatedPost;

import javax.annotation.Nonnull;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping("/admin/moderation")
public class ModerationController {
    static final DateTimeFormatter viewTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd MMM");

    private static final String CATEGORY_MODERATION = "admin/moderation";
    private static final String VIEW_MODERATION_POSTS = CATEGORY_MODERATION + "/posts";
    private static final String VIEW_MODERATION_POST = CATEGORY_MODERATION + "/post";
    private static final String VIEW_MODERATION_POST_SAVED = CATEGORY_MODERATION + "/postSaved";
    private static final String VIEW_MODERATION_POST_SAVE_ERROR = CATEGORY_MODERATION + "/postSaveError";

    private static final int DEFAULT_ON_PAGE_COUNT = 20;

    private static final Logger log = LoggerFactory.getLogger(ModerationController.class);

    private final PremoderationQueueService premoderationQueueService;
    private final AdminController adminController;
    private final PostFormValidator postFormValidator;

    public ModerationController(@Nonnull PremoderationQueueService premoderationQueueService,
                                @Nonnull AdminController adminController,
                                @Nonnull PostFormValidator postFormValidator) {
        this.premoderationQueueService = requireNonNull(premoderationQueueService, "premoderationQueueService");
        this.adminController = requireNonNull(adminController, "adminController");
        this.postFormValidator = requireNonNull(postFormValidator, "postFormValidator");
    }

    @GetMapping("/posts/page/{page}/")
    public String posts(Model model, @PathVariable("page") int page) {
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

    //todo test
    @GetMapping("/posts/{id}/")
    public String post(Model model, @PathVariable("id") long id) {
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

    // todo test
    @PostMapping(value = "/posts/{id}/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String post(Model model, @PathVariable("id") long id, @RequestParam Map<String, String> form) {
        log.info("post(): id={}, form={}", id, form);
        Optional<UnmoderatedPost> existedPost = premoderationQueueService.getPost(id);
        if (existedPost.isEmpty()) {
            log.error("Post does not exist: postId={}", id);
            return error(Error.POST_NOT_FOUND, id, model);
        }

        PostForm postForm = new PostForm(form);
        Optional<PostFormValidator.ValidationError> validationError = postFormValidator.validateForm(postForm);
        if (validationError.isPresent()) {
            log.error("Form data is invalid: validationError={}", validationError.get());
            return error(validationError.get(), id, model);
        }

        UnmoderatedPost postToUpdate = toUnmoderatedPost(existedPost.get(), postForm);
        if (!premoderationQueueService.updatePost(postToUpdate)) {
            log.error("Update failed: post={}", postToUpdate);
            return error(Error.UPDATE_FAILED, id, model);
        }
        if (postForm.isPublish()) {
            if (!premoderationQueueService.publishPost(id)) {
                log.error("Publish failed: postId={}", id);
                return error(Error.PUBLISH_FAILED, id, model);
            }
        }
        return VIEW_MODERATION_POST_SAVED;
    }

    private static Post toPost(UnmoderatedPost unmoderatedPost) {
        Channel channel = Channel.getById(unmoderatedPost.getChannelId());
        return Post.builder()
                .setId(unmoderatedPost.getId().orElse(null))
                .setChannel(channel.getName())
                .setChannelId(channel.getId())
                .setTitle(unmoderatedPost.getTitle().orElse(null))
                .setText(unmoderatedPost.getText().orElse(null))
                .setImages(unmoderatedPost.getImages().stream()
                        .map(ModerationController::toImage)
                        .collect(Collectors.toList()))
                .setCreatedDt(unmoderatedPost.getCreatedDt().map(viewTimeFormatter::format).orElse(null))
                .setChangedDt(unmoderatedPost.getChangedDt().map(viewTimeFormatter::format).orElse(null))
                .build();
    }

    @Nonnull
    static Image toImage(@Nonnull PostImage postImage) {
        requireNonNull(postImage, "postImage");
        String title = postImage.getSrc().replaceAll("^.*/([\\-\\w]+\\.\\w+)", "$1");
        if (postImage.getWidth().isPresent() && postImage.getHeight().isPresent()) {
            title += " [" + postImage.getWidth().get() + 'x' + postImage.getHeight().get() + ']';
        }
        return new Image(postImage.getSrc(), title);
    }

    private static UnmoderatedPost toUnmoderatedPost(UnmoderatedPost existedPost, PostForm postForm) {
        return existedPost.copy()
                .setChannelId(Integer.parseInt(postForm.getChannelId()))
                .setTitle(postForm.getTitle())
                .setText(postForm.getText())
                .setImages(postForm.getImage() != null
                        ? Collections.singletonList(existedPost.getImages().get(Integer.parseInt(postForm.getImage())))
                        : null)
                .build();
    }

    private static String error(ErrorCode errorCode, long postId, Model model) {
        model.addAttribute("postId", postId);
        model.addAttribute("errorCode", errorCode.getCode());
        model.addAttribute("errorDescription", errorCode.getDescription());
        return VIEW_MODERATION_POST_SAVE_ERROR;
    }

    private enum Error implements ErrorCode {
        POST_NOT_FOUND("Поста по заданному идентификатору не существует"),
        UPDATE_FAILED("Не удалось обновить данные поста"),
        PUBLISH_FAILED("Не удалось опубликовать пост");

        private final String description;

        Error(String description) {
            this.description = description;
        }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
