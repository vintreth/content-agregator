package ru.skogmark.aggregator.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final String CATEGORY_ADMIN = "admin";
    private static final String VIEW_ADMIN_INDEX = CATEGORY_ADMIN + "/index";
    private static final String VIEW_ADMIN_ERROR_404 = CATEGORY_ADMIN + "/error404";

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

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
}
