package by.training.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "post")
public class PostModel extends Model {

    private static final long  serialVersionUID = 7372820574885171442L;

    @Column(name = "name", nullable = false, length = 255)
    private String             name;

    @Column(name = "logo", nullable = false, length = 255)
    private String             logo;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String             text;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date               date;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.PERSIST}, targetEntity = UserModel.class, optional = false)
    private UserModel          creator;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<CommentModel> comments;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.PERSIST}, targetEntity = PostModel.class)
    @JoinTable(name = "post_user", joinColumns = @JoinColumn(name = "id_post", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_user", nullable = false, updatable = false))
    private List<UserModel>    users;

    public PostModel() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
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

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(final List<CommentModel> comments) {
        this.comments = comments;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(final List<UserModel> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Post [id=" + super.getId() + ", name=" + name + ", logo=" + logo + ", text=" + text
                + ", created=" + date + ", creator=" + creator + "]";
    }

}
