package by.training.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "topic")
public class TopicEntity extends AbstractEntity {

    private static final long       serialVersionUID = 8849827068678797244L;

    @Column(name = "name", nullable = false, length = 255)
    private String                  name;

    @Column(name = "path", unique = true, nullable = false, length = 255)
    private String                  path;

    @Column(name = "description", nullable = false, length = 255)
    private String                  description;

    @Column(name = "access", nullable = false)
    private boolean                 access;

    @ManyToOne(targetEntity = UserEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private UserEntity              creator;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "topic")
    private Set<NotificationEntity> notifications;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "topic")
    private Set<PostEntity>         posts;

    @JsonIgnore
    @ManyToMany(targetEntity = UserEntity.class)
    @JoinTable(name = "topic_user", joinColumns = @JoinColumn(name = "topic_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false))
    private Set<UserEntity>         users;

    public TopicEntity() {
        super();
    }

    public TopicEntity(final String name, final String path, final String description,
            final boolean access, final UserEntity creator) {
        super();
        this.name = name;
        this.path = path;
        this.description = description;
        this.access = access;
        this.creator = creator;
        users = new HashSet<>();
        users.add(creator);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
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

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(final UserEntity creator) {
        this.creator = creator;
    }

    public Set<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(final Set<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    public Set<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(final Set<PostEntity> posts) {
        this.posts = posts;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(final Set<UserEntity> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "TopicEntity [id=" + super.getId() + ", name=" + name + ", path=" + path
                + ", description=" + description + ", access=" + access + ", creator=" + creator
                + "]";
    }

}
