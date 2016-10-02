package by.training.bean;

import java.io.Serializable;
import java.util.Date;

import by.training.entity.PostEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;

public class Post implements Serializable {

    private static final long serialVersionUID = 1380434781496818302L;

    private long              id;
    private String            text;
    private Date              date;
    private UserEntity        creator;
    private TopicEntity       topic;

    public Post() {
    }

    public Post(final long id, final String text, final Date date, final UserEntity creator,
            final TopicEntity topic) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.creator = creator;
        this.topic = topic;
    }

    public Post(final PostEntity post) {
        id = post.getId();
        text = post.getText();
        date = post.getDate();
        creator = post.getCreator();
        topic = post.getTopic();
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(final UserEntity creator) {
        this.creator = creator;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(final TopicEntity topic) {
        this.topic = topic;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Post other = (Post) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", text=" + text + ", created=" + date + ", creator=" + creator
                + "]";
    }

}
