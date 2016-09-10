package by.training.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class UserModel extends Model implements UserDetails {

    private static final long       serialVersionUID = 7372820574885171442L;

    @Column(name = "login", unique = true, nullable = false, length = 255)
    private String                  login;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String                  password;

    @ManyToMany(targetEntity = RoleModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false))
    private List<RoleModel>         roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<NotificationModel> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "inviter")
    private List<NotificationModel> notificationsFrom;

    @JsonIgnore
    @OneToMany(mappedBy = "creator")
    private List<PostModel>         posts;

    @JsonIgnore
    @ManyToMany(targetEntity = TopicModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "topic_user", joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "topic_id", nullable = false, updatable = false))
    private List<TopicModel>        topics;

    public UserModel() {
        super();
    }

    public UserModel(final String login, final String password, final List<RoleModel> roles) {
        super();
        this.login = login;
        this.password = password;
        this.roles = roles;

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(final List<RoleModel> roles) {
        this.roles = roles;
    }

    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(final List<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationModel> getNotificationsFrom() {
        return notificationsFrom;
    }

    public void setNotificationsFrom(final List<NotificationModel> notificationsFrom) {
        this.notificationsFrom = notificationsFrom;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

}
