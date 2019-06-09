package ru.skogmark.aggregator.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final String CATEGORY_ADMIN = "admin";
    private static final String VIEW_ADMIN_INDEX = CATEGORY_ADMIN + "/index";

    @GetMapping("/")
    public String index() {
        return VIEW_ADMIN_INDEX;
    }
}
