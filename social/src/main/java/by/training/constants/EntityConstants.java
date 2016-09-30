package by.training.constants;

public abstract class EntityConstants {

    public static abstract class ElementsCount {

        public static final int NOTIFICATION = 10;
        public static final int POST         = 5;
        public static final int TOPIC        = 15;

    }

    public static abstract class Sort {

        public static final org.springframework.data.domain.Sort NOTIFICATION = new org.springframework.data.domain.Sort(
                org.springframework.data.domain.Sort.Direction.DESC,
                Structure.NotificationFields.DATE);

    }

    public static abstract class Structure {

        public static abstract class Entities {

            public static final String NOTIFICATION = "notification";
            public static final String POST         = "post";
            public static final String ROLE         = "role";
            public static final String TOPIC        = "topic";
            public static final String USER         = "user";

        }

        public static abstract class AbstractFields {

            public static final String ID = "id";

        }

        public static abstract class NotificationFields {

            public static final String ID      = AbstractFields.ID;
            public static final String DATE    = "date";
            public static final String USER    = "user";
            public static final String INVITER = "inviter";
            public static final String TOPIC   = "topic";

        }

        public static abstract class PostFields {

            public static final String ID          = AbstractFields.ID;
            public static final String TEXT        = "text";
            public static final String DATE        = "date";
            public static final String CREATOR     = "creator";
            public static final String TOPIC       = "topic";
            public static final String PARENT_POST = "parentPost";

        }

        public static abstract class RoleFields {

            public static final String ID   = AbstractFields.ID;
            public static final String NAME = "name";

        }

        public static abstract class TopicFields {

            public static final String ID          = AbstractFields.ID;
            public static final String NAME        = "name";
            public static final String PATH        = "path";
            public static final String DESCRIPTION = "description";
            public static final String ACCESS      = "access";
            public static final String CREATOR     = "creator";

        }

        public static abstract class UserFields {

            public static final String ID       = AbstractFields.ID;
            public static final String LOGIN    = "login";
            public static final String PASSWORD = "password";
            public static final String ROLE     = "role";

        }

        public static abstract class RelationFields {

            public static final String NOTIFICATIONS = "notifications";
            public static final String POSTS         = "posts";
            public static final String TOPICS        = "topics";
            public static final String USERS         = "users";

        }

    }

}
