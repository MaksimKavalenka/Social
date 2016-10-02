package by.training.bean;

import java.util.Date;

import by.training.entity.PostEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;

public class PostWithCommentsCount extends Post {

    private static final long serialVersionUID = 8756882736414461376L;

    private long              commentsCount;

    public PostWithCommentsCount() {
        super();
    }

    public PostWithCommentsCount(final long id, final String text, final Date date,
            final UserEntity creator, final TopicEntity topic, final long commentsCount) {
        super(id, text, date, creator, topic);
        this.commentsCount = commentsCount;
    }

    public PostWithCommentsCount(final PostEntity post, final long commentsCount) {
        super(post);
        this.commentsCount = commentsCount;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(final long commentsCount) {
        this.commentsCount = commentsCount;
    }

    @Override
    public String toString() {
        return "PostWithCommentsCount [id=" + super.getId() + ", text=" + super.getText()
                + ", created=" + super.getDate() + ", creator=" + super.getCreator()
                + ", commentsCount=" + commentsCount + "]";
    }

}
