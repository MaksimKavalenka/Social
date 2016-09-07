package by.training.controller;

import static by.training.constants.PageConstants.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @RequestMapping(value = {"/", FEED_URI}, method = RequestMethod.GET)
    public String feedPage() {
        return REDIRECT + FEED_DEFAULT_URI;
    }

    @RequestMapping(value = {TOPIC_URI}, method = RequestMethod.GET)
    public String topicPage() {
        return REDIRECT + TOPICS_DEFAULT_URI;
    }

    @RequestMapping(value = {TOPICS_URI}, method = RequestMethod.GET)
    public String topicsPage() {
        return REDIRECT + TOPICS_DEFAULT_URI;
    }

    @RequestMapping(value = {LOGIN_URI, REGISTRATION_URI, TOPIC_ADD_URI, FEED_FULL_URI,
            TOPIC_DEFAULT_URI, TOPICS_FULL_URI}, method = RequestMethod.GET)
    public String page() {
        return INDEX_PATH;
    }

}
