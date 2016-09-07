package by.training.constants;

public abstract class ModelStructureConstants {

    public abstract static class Models {

        public static final String NOTIFICATION = "notification";
        public static final String POST         = "post";
        public static final String ROLE         = "role";
        public static final String TOPIC        = "topic";
        public static final String USER         = "user";

    }

    public abstract static class ModelFields {

        public static final String ID = "id";

    }

    public abstract static class NotificationFields {

        public static final String ID      = ModelFields.ID;
        public static final String DATE    = "date";
        public static final String USER    = "user";
        public static final String INVITER = "inviter";
        public static final String TOPIC   = "topic";

    }

    public abstract static class PostFields {

        public static final String ID          = ModelFields.ID;
        public static final String TEXT        = "text";
        public static final String DATE        = "date";
        public static final String CREATOR     = "creator";
        public static final String TOPIC       = "topic";
        public static final String PARENT_POST = "parentPost";

    }

    public abstract static class RoleFields {

        public static final String ID   = ModelFields.ID;
        public static final String NAME = "name";

    }

    public abstract static class TopicFields {

        public static final String ID          = ModelFields.ID;
        public static final String NAME        = "name";
        public static final String PATH        = "path";
        public static final String DESCRIPTION = "description";
        public static final String ACCESS      = "access";
        public static final String CREATOR     = "creator";

    }

    public abstract static class UserFields {

        public static final String ID       = ModelFields.ID;
        public static final String LOGIN    = "login";
        public static final String PASSWORD = "password";
        public static final String ROLE     = "role";

    }

    public abstract static class RelationFields {

        public static final String NOTIFICATIONS = "notifications";
        public static final String POSTS         = "posts";
        public static final String TOPICS        = "topics";
        public static final String USERS         = "users";

    }

}
