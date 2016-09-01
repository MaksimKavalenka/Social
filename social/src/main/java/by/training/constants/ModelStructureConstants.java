package by.training.constants;

public abstract class ModelStructureConstants {

    public abstract static class Models {

        public static final String COMMENT = "comment";
        public static final String POST    = "post";
        public static final String ROLE    = "role";
        public static final String USER    = "user";

    }

    public abstract static class ModelFields {

        public static final String ID = "id";

    }

    public abstract static class CommentFields {

        public static final String ID      = ModelFields.ID;
        public static final String TEXT    = "text";
        public static final String DATE    = "date";
        public static final String CREATOR = "creator";
        public static final String POST    = "post";

    }

    public abstract static class PostFields {

        public static final String ID      = ModelFields.ID;
        public static final String NAME    = "name";
        public static final String LOGO    = "logo";
        public static final String TEXT    = "text";
        public static final String DATE    = "date";
        public static final String CREATOR = "creator";

    }

    public abstract static class RoleFields {

        public static final String ID   = ModelFields.ID;
        public static final String NAME = "name";

    }

    public abstract static class UserFields {

        public static final String ID       = ModelFields.ID;
        public static final String LOGIN    = "login";
        public static final String PASSWORD = "password";
        public static final String ROLE     = "role";

    }

    public abstract static class RelationFields {

        public static final String ANSWERS       = "answers";
        public static final String COMMENTS      = "comments";
        public static final String CREATED_POSTS = "createdPosts";
        public static final String POSTS         = "posts";
        public static final String USERS         = "users";

    }

}
