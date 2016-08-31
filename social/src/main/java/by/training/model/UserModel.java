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

    private static final long  serialVersionUID = 7372820574885171442L;

    @Column(name = "login", unique = true, nullable = false, length = 255)
    private String             login;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String             password;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.PERSIST}, targetEntity = RoleModel.class, optional = false)
    private RoleModel          role;

    @JsonIgnore
    @OneToMany(mappedBy = "creator")
    private List<PostModel>    createdPosts;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.PERSIST}, targetEntity = PostModel.class)
    @JoinTable(name = "post_user", joinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_post", nullable = false, updatable = false))
    private List<PostModel>    posts;

    @JsonIgnore
    @OneToMany(mappedBy = "creator")
    private List<CommentModel> comments;

    public UserModel() {
        super();
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

    public List<PostModel> getCreatedPosts() {
        return createdPosts;
    }

    public void setCreatedPosts(final List<PostModel> createdPosts) {
        this.createdPosts = createdPosts;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(final List<PostModel> posts) {
        this.posts = posts;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(final List<CommentModel> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "User [id=" + super.getId() + ", login=" + login + "]";
    }

}
