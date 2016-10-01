package by.training.constants;

public abstract class UrlConstants {

    public static final String ID_KEY    = "/{id:[0-9]{1,}}";
    public static final String PAGE_KEY  = "/{page:[0-9]{1,}}";
    public static final String PATH_KEY  = "/{path:[a-z0-9_]{1,}}";
    public static final String VALUE_KEY = "/{value}";

    public abstract class Page {

        private static final String ADD_OPERATION     = "/add";
        private static final String EDIT_OPERATION    = "/edit";

        public static final String  LOGIN_URL         = "/login";
        public static final String  REGISTRATION_URL  = "/register";

        public static final String  FEED_URL          = "/feed";
        public static final String  NOTIFICATIONS_URL = "/notifications";
        public static final String  SEARCH_URL        = "/search";
        public static final String  TOPICS_URL        = "/topics";

        public static final String  POST_URL          = "/post";
        public static final String  POST_FULL_URL     = POST_URL + PATH_KEY;

        public static final String  PROFILE_URL       = "/profile";
        public static final String  PROFILE_PHOTO_URL = PROFILE_URL + "/photo";

        public static final String  TOPIC_URL         = "/topic";
        public static final String  TOPIC_ADD_URL     = TOPIC_URL + ADD_OPERATION;
        public static final String  TOPIC_EDIT_URL    = TOPIC_URL + PATH_KEY + EDIT_OPERATION;
        public static final String  TOPIC_FULL_URL    = TOPIC_URL + PATH_KEY;

    }

    public abstract class Rest {

        public static final String NOTIFICATIONS_URL = "/notifications";
        public static final String POSTS_URL         = "/posts";
        public static final String TOPICS_URL        = "/topics";
        public static final String UPLOAD_URL        = "/upload";
        public static final String USERS_URL         = "/users";

    }

}
