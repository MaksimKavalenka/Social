package by.training.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "topic")
public class TopicModel extends Model {

    private static final long       serialVersionUID = 8849827068678797244L;

    @Column(name = "name", nullable = false, length = 255)
    private String                  name;

    @Column(name = "description", nullable = false, length = 255)
    private String                  description;

    @Column(name = "access", nullable = false)
    private boolean                 access;

    @ManyToOne(targetEntity = UserModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    private UserModel               creator;

    @JsonIgnore
    @OneToMany(mappedBy = "topic")
    private List<PostModel>         posts;

    @JsonIgnore
    @ManyToMany(targetEntity = UserModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "topic_user", joinColumns = @JoinColumn(name = "id_topic", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    private List<UserModel>         users;

    @JsonIgnore
    @OneToMany(mappedBy = "topic")
    private List<NotificationModel> notifications;

    public TopicModel() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(final boolean access) {
        this.access = access;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(final UserModel creator) {
        this.creator = creator;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(final List<PostModel> posts) {
        this.posts = posts;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(final List<UserModel> users) {
        this.users = users;
    }

    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(final List<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "Topic [id=" + super.getId() + ", name=" + name + ", description=" + description
                + ", access=" + access + ", creator=" + creator + "]";
    }

}
