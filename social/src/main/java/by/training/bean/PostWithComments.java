package by.training.bean;

import java.util.Date;
import java.util.Set;

import by.training.entity.PostEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;

public class PostWithComments extends Post {

    private static final long     serialVersionUID = -721742350544620933L;

    private Set<PostWithComments> posts;

    public PostWithComments() {
        super();
    }

    public PostWithComments(final long id, final String text, final Date date,
            final UserEntity creator, final TopicEntity topic, final Set<PostWithComments> posts) {
        super(id, text, date, creator, topic);
        this.posts = posts;
    }

    public PostWithComments(final PostEntity post, final Set<PostWithComments> posts) {
        super(post);
        this.posts = posts;
    }

    public Set<PostWithComments> getPosts() {
        return posts;
    }

    public void setPosts(final Set<PostWithComments> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "PostWithComments [id=" + super.getId() + ", text=" + super.getText() + ", created="
                + super.getDate() + ", creator=" + super.getCreator() + ", posts=" + posts + "]";
    }

}
