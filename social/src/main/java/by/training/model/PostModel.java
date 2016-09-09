package by.training.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "post")
public class PostModel extends Model {

    private static final long      serialVersionUID = 7372820574885171442L;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String                 text;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date                   date;

    @ManyToOne(targetEntity = UserModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    private UserModel              creator;

    @ManyToOne(targetEntity = TopicModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    private TopicModel             topic;

    @ManyToOne(targetEntity = PostModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    private PostModel              parentPost;

    @JsonIgnore
    @OneToMany(mappedBy = "parentPost")
    private Set<PostModel>         posts;

    @JsonIgnore
    @OneToMany(mappedBy = "topic")
    private Set<NotificationModel> notifications;

    public PostModel() {
        super();
    }

    public PostModel(final String text, final UserModel creator, final TopicModel topic,
            final PostModel parentPost) {
        super();
        date = new Date();
        this.text = text;
        this.creator = creator;
        this.topic = topic;
        this.parentPost = parentPost;
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

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(final UserModel creator) {
        this.creator = creator;
    }

    public TopicModel getTopic() {
        return topic;
    }

    public void setTopic(final TopicModel topic) {
        this.topic = topic;
    }

    public PostModel getParentPost() {
        return parentPost;
    }

    public void setParentPost(final PostModel parentPost) {
        this.parentPost = parentPost;
    }

    public Set<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(final Set<PostModel> posts) {
        this.posts = posts;
    }

    public Set<NotificationModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(final Set<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "Post [id=" + super.getId() + ", text=" + text + ", created=" + date + ", creator="
                + creator + ", parentPost=" + parentPost + "]";
    }

}
