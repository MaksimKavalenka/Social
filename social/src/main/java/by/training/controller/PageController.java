package by.training.controller;

import static by.training.constants.PageConstants.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String tracksPage() {
        return REDIRECT + FEED_URI;
    }

    @RequestMapping(value = {LOGIN_URI, REGISTRATION_URI, FEED_URI}, method = RequestMethod.GET)
    public String page() {
        return INDEX_PATH;
    }

}
