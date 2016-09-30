package by.training.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "post")
public class PostEntity extends AbstractEntity {

    private static final long serialVersionUID = 7372820574885171442L;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String            text;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date              date;

    @ManyToOne(targetEntity = UserEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private UserEntity        creator;

    @ManyToOne(targetEntity = TopicEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private TopicEntity       topic;

    @JsonIgnore
    @ManyToOne(targetEntity = PostEntity.class, cascade = {
            CascadeType.DETACH}, fetch = FetchType.LAZY)
    private PostEntity        parentPost;

    @OneToMany(cascade = {CascadeType.REMOVE,
            CascadeType.DETACH}, fetch = FetchType.EAGER, mappedBy = "parentPost")
    private Set<PostEntity>   posts;

    public PostEntity() {
        super();
    }

    public PostEntity(final String text, final UserEntity creator, final TopicEntity topic,
            final PostEntity parentPost) {
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

    public PostEntity getParentPost() {
        return parentPost;
    }

    public void setParentPost(final PostEntity parentPost) {
        this.parentPost = parentPost;
    }

    public Set<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(final Set<PostEntity> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "PostEntity [id=" + super.getId() + ", text=" + text + ", created=" + date
                + ", creator=" + creator + "]";
    }

}
