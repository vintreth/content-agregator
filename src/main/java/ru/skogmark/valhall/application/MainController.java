package ru.skogmark.valhall.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/ping")
    @ResponseBody
    public String ping() {
        log.info("MainController ping()");
        return "ok";
    }
}
