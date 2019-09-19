package com.malex.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class ViewController {

    /**
     * Home page
     */
    private static final String INDEX_HTML = "index.html";

    @GetMapping(value = {"/", "/{page}"}, produces = "text/html")
    public String homePage(@PathVariable(name = "page", required = false) String page) {
        if (StringUtils.isNotBlank(page)) {
            log.debug(page);
        }
        return INDEX_HTML;
    }
}
