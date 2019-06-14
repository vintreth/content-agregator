package ru.skogmark.aggregator.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.skogmark.aggregator.channel.Channel;
import ru.skogmark.aggregator.channel.Source;
import ru.skogmark.aggregator.core.SourceContext;
import ru.skogmark.aggregator.core.Timetable;
import ru.skogmark.aggregator.core.Worker;
import ru.skogmark.aggregator.core.content.Parser;
import ru.skogmark.aggregator.parser.vk.VkApiParser;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final String CATEGORY_ADMIN = "admin";
    private static final String VIEW_ADMIN_INDEX = CATEGORY_ADMIN + "/index";
    private static final String VIEW_ADMIN_ERROR_404 = CATEGORY_ADMIN + "/error404";
    private static final String VIEW_ADMIN_DOWNLOAD = CATEGORY_ADMIN + "/download";

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final Worker worker;
    private final VkApiParser vkApiParser;

    public AdminController(@Nonnull Worker worker, @Nonnull VkApiParser vkApiParser) {
        this.worker = requireNonNull(worker, "worker");
        this.vkApiParser = requireNonNull(vkApiParser, "vkApiParser");
    }

    @GetMapping("/")
    public String index() {
        log.info("index()");
        return VIEW_ADMIN_INDEX;
    }

    @GetMapping("/error404/")
    public String error404() {
        log.info("error404()");
        return VIEW_ADMIN_ERROR_404;
    }

    @GetMapping("/download/")
    public String download(Model model) {
        log.info("download()");
        model.addAttribute("sources", List.of(Source.values()));
        model.addAttribute("channels", List.of(Channel.values()));
        return VIEW_ADMIN_DOWNLOAD;
    }

    @PostMapping(value = "/download/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String download(@RequestParam Map<String, String> form) {
        log.info("download(): form={}", form);
        DownloadForm downloadForm = new DownloadForm(form);
        // todo validate form
        int sourceId = Integer.parseInt(downloadForm.getSourceId());
        worker.parseContent(SourceContext.builder()
                .setSourceId(sourceId)
                .setTimetable(Timetable.of(Collections.emptySet()))
                .setParser(getParser(Source.getById(sourceId)))
                .setParserLimit(Integer.parseInt(downloadForm.getLimit()))
                .build(), Integer.parseInt(downloadForm.getChannelId()));
        return VIEW_ADMIN_DOWNLOAD;
    }

    // todo test
    Parser getParser(Source source) {
        switch (source) {
            case VK_LEPRA:
                return vkApiParser;
            default:
                throw new IllegalArgumentException("Unknown source: source=" + source);
        }
    }
}
