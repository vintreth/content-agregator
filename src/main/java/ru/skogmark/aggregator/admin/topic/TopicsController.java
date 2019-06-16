package ru.skogmark.aggregator.admin.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/topics")
public class TopicsController {
    private static final String CATEGORY_TOPIC = "admin/topics";
    private static final String VIEW_TOPICS = CATEGORY_TOPIC + "/index";

    private static final Logger log = LoggerFactory.getLogger(TopicsController.class);

    @GetMapping("/")
    public String getTopics(Model model) {
        log.info("getTopics()");
        return VIEW_TOPICS;
    }
}
