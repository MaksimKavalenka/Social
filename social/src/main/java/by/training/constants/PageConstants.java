package by.training.constants;

public abstract class PageConstants {

    private static final String ADD_OPERATION      = "/add";
    private static final String PAGE_OPERATION     = "/page";

    private static final String ID_PATTERN         = "/{id:[0-9]{1,}}";
    private static final String PAGE_PATTERN       = "/{page:[0-9]{1,}}";
    private static final String PAGE_DEFAULT       = "/1";
    
    private static final String TOPIC_URI          = "/topic";

    public static final String  REDIRECT           = "redirect:";
    public static final String  INDEX_PATH         = "/index";

    public static final String  LOGIN_URI          = "/login";
    public static final String  REGISTRATION_URI   = "/register";

    public static final String  FEED_URI           = "/feed";
    public static final String  TOPICS_URI         = "/topics";

    public static final String  FEED_DEFAULT_URI   = FEED_URI + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  TOPIC_DEFAULT_URI  = TOPIC_URI + ID_PATTERN + PAGE_OPERATION + PAGE_DEFAULT;
    public static final String  TOPICS_DEFAULT_URI = TOPICS_URI + PAGE_OPERATION + PAGE_DEFAULT;

    public static final String  TOPIC_ADD_URI      = TOPIC_URI + ADD_OPERATION;

    public static final String  FEED_FULL_URI      = FEED_URI + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  TOPIC_FULL_URI     = TOPIC_URI + ID_PATTERN + PAGE_OPERATION + PAGE_PATTERN;
    public static final String  TOPICS_FULL_URI    = TOPICS_URI + PAGE_OPERATION + PAGE_PATTERN;

}
