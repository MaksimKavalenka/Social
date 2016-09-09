package by.training.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class UserModel extends Model {

    private static final long       serialVersionUID = 7372820574885171442L;

    @Column(name = "login", unique = true, nullable = false, length = 255)
    private String                  login;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String                  password;

    @ManyToOne(targetEntity = RoleModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    private RoleModel               role;

    @JsonIgnore
    @OneToMany(mappedBy = "topic")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<NotificationModel> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "creator")
    private List<PostModel>         posts;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = TopicModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "topic_user", joinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_topic", nullable = false, updatable = false))
    private List<TopicModel>        topics;

    public UserModel() {
        super();
    }

    public UserModel(final String login, final String password, final RoleModel role) {
        super();
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(final RoleModel role) {
        this.role = role;
    }

    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(final List<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(final List<PostModel> posts) {
        this.posts = posts;
    }

    public List<TopicModel> getTopics() {
        return topics;
    }

    public void setTopics(final List<TopicModel> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "User [id=" + super.getId() + ", login=" + login + "]";
    }

}
