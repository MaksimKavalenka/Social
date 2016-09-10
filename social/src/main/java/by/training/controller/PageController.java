package by.training.controller;

import static by.training.constants.DefaultConstants.*;
import static by.training.constants.UrlConstants.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = {"/", FEED_URL}, method = RequestMethod.GET)
    public String defaultFeedPage() {
        return REDIRECT + FEED_URL + "?" + DEFAULT_PAGE;
    }

    @RequestMapping(value = {TOPIC_URL}, method = RequestMethod.GET)
    public String defaultTopicPage() {
        return REDIRECT + TOPIC_URL + "?" + DEFAULT_PAGE;
    }

    @RequestMapping(value = {TOPICS_URL}, method = RequestMethod.GET)
    public String defaultTopicsPage() {
        return REDIRECT + TOPICS_URL + "?" + DEFAULT_PAGE;
    }

    @RequestMapping(value = {SEARCH_URL}, method = RequestMethod.GET)
    public String defaultSearchPage() {
        return REDIRECT + SEARCH_URL + "?" + DEFAULT_VALUE + "&" + DEFAULT_PAGE;
    }

    @RequestMapping(value = {LOGIN_URL, REGISTRATION_URL,
            TOPIC_ADD_URL}, method = RequestMethod.GET)
    public String editPages() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = {FEED_URL, TOPIC_URL, TOPICS_URL}, params = {
            "page"}, method = RequestMethod.GET)
    public String mainPages() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = {SEARCH_URL}, params = {"value", "page"}, method = RequestMethod.GET)
    public String searchPage() {
        return DEFAULT_PATH;
    }

}
