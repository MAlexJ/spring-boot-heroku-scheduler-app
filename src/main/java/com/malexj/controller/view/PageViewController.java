package com.malexj.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageViewController {

    public static final String REDIRECT = "redirect:/";

    @GetMapping(value = "/logs")
    public ModelAndView handleLogsRedirect() {
        return new ModelAndView(REDIRECT);
    }

    @GetMapping(value = "/settings")
    public ModelAndView handleSettingsRedirect() {
        return new ModelAndView(REDIRECT);
    }
}
