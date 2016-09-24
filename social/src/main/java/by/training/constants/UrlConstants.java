package by.training.constants;

public abstract class UrlConstants {

    private static final String ADD_OPERATION     = "/add";
    private static final String EDIT_OPERATION    = "/edit";

    public static final String  PROFILE_PATH      = "/profile";
    public static final String  TOPIC_PATH        = "/topic";

    public static final String  ID_KEY            = "/{id:[0-9]{1,}}";
    public static final String  PAGE_KEY          = "/{page:[0-9]{1,}}";
    public static final String  PATH_KEY          = "/{path:[a-z0-9_]{1,}}";
    public static final String  VALUE_KEY         = "/{value}";

    public static final String  LOGIN_URL         = "/login";
    public static final String  REGISTRATION_URL  = "/register";
    public static final String  PROFILE_URL       = PROFILE_PATH;
    public static final String  PROFILE_PHOTO_URL = PROFILE_PATH + "/photo";

    public static final String  TOPIC_ADD_URL     = TOPIC_PATH + ADD_OPERATION;
    public static final String  TOPIC_EDIT_URL    = TOPIC_PATH + PATH_KEY + EDIT_OPERATION;

    public static final String  FEED_URL          = "/feed";
    public static final String  TOPIC_URL         = TOPIC_PATH + PATH_KEY;
    public static final String  TOPICS_URL        = "/topics";
    public static final String  POST_URL          = "/post" + PATH_KEY;
    public static final String  NOTIFICATIONS_URL = "/notifications";
    public static final String  SEARCH_URL        = "/search";

}
