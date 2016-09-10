package by.training.constants;

public abstract class UrlConstants {

    private static final String PATH_KEY         = "/{path:[a-z0-9._]{1,}}";

    private static final String ADD_OPERATION    = "/add";

    private static final String TOPIC_PATH       = "/topic";

    public static final String  LOGIN_URL        = "/login";
    public static final String  REGISTRATION_URL = "/register";

    public static final String  TOPIC_ADD_URL    = TOPIC_PATH + ADD_OPERATION;

    public static final String  FEED_URL         = "/feed";
    public static final String  TOPIC_URL        = TOPIC_PATH + PATH_KEY;
    public static final String  TOPICS_URL       = "/topics";
    public static final String  SEARCH_URL       = "/search";

}
