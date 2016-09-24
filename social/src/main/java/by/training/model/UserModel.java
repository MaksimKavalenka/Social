package by.training.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class UserModel extends Model implements UserDetails {

    private static final long      serialVersionUID = 7372820574885171442L;

    @JsonIgnore
    private boolean                accountNonExpired;

    @JsonIgnore
    private boolean                accountNonLocked;

    @JsonIgnore
    private boolean                credentialsNonExpired;

    @JsonIgnore
    private boolean                enabled;

    @Column(name = "login", unique = true, nullable = false, length = 255)
    private String                 login;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String                 password;

    @Column(name = "photo", length = 255)
    private String                 photo;

    @JsonIgnore
    @ManyToMany(targetEntity = RoleModel.class, cascade = {
            CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false, updatable = false))
    private Set<GrantedAuthority>  roles;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "user")
    private Set<NotificationModel> notifications;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "inviter")
    private Set<NotificationModel> sentNotifications;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.DETACH}, mappedBy = "creator")
    private Set<PostModel>         posts;

    @JsonIgnore
    @ManyToMany(targetEntity = TopicModel.class, cascade = {CascadeType.REMOVE, CascadeType.DETACH})
    @JoinTable(name = "topic_user", joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "topic_id", nullable = false, updatable = false))
    private Set<TopicModel>        topics;

    public UserModel() {
        super();
    }

    public UserModel(final String login, final String password, final Set<GrantedAuthority> roles) {
        this(true, true, true, true, login, password, null, roles);
    }

    public UserModel(final boolean accountNonExpired, final boolean accountNonLocked,
            final boolean credentialsNonExpired, final boolean enabled, final String login,
            final String password, final String photo, final Set<GrantedAuthority> roles) {
        super();
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.login = login;
        this.password = password;
        this.photo = photo;
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(final Set<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public Set<NotificationModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(final Set<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    public Set<NotificationModel> getSentNotifications() {
        return sentNotifications;
    }

    public void setSentNotifications(final Set<NotificationModel> sentNotifications) {
        this.sentNotifications = sentNotifications;
    }

    public Set<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(final Set<PostModel> posts) {
        this.posts = posts;
    }

    public Set<TopicModel> getTopics() {
        return topics;
    }

    public void setTopics(final Set<TopicModel> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "User [id=" + super.getId() + ", login=" + login + "]";
    }

}
