package by.training.controller;

import static by.training.constants.DefaultConstants.*;
import static by.training.constants.UrlConstants.Page.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = {"/", FEED_URL}, method = RequestMethod.GET)
    public String defaultFeedPage() {
        return REDIRECT + FEED_URL + "?" + DEFAULT_PAGE;
    }

    @RequestMapping(value = SEARCH_URL, method = RequestMethod.GET)
    public String defaultSearchPage() {
        return REDIRECT + SEARCH_URL + "?" + DEFAULT_VALUE + "&" + DEFAULT_PAGE;
    }

    @RequestMapping(value = {TOPIC_FULL_URL}, method = RequestMethod.GET)
    public String defaultTopicPage(@PathVariable("path") final String path) {
        return REDIRECT + TOPIC_URL + "/" + path + "?" + DEFAULT_PAGE;
    }

    @RequestMapping(value = TOPICS_URL, method = RequestMethod.GET)
    public String defaultTopicsPage() {
        return REDIRECT + TOPICS_URL + "?" + DEFAULT_PAGE;
    }

    @RequestMapping(value = {LOGIN_URL, REGISTRATION_URL, PROFILE_URL, PROFILE_PHOTO_URL,
            TOPIC_ADD_URL, TOPIC_EDIT_URL}, method = RequestMethod.GET)
    public String editPages() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = {FEED_URL, TOPIC_FULL_URL,
            TOPICS_URL}, params = "page", method = RequestMethod.GET)
    public String mainPages() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = NOTIFICATIONS_URL, method = RequestMethod.GET)
    public String notificationsPage() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = POST_FULL_URL, params = "id", method = RequestMethod.GET)
    public String postPage() {
        return DEFAULT_PATH;
    }

    @RequestMapping(value = SEARCH_URL, params = {"value", "page"}, method = RequestMethod.GET)
    public String searchPage() {
        return DEFAULT_PATH;
    }

}
