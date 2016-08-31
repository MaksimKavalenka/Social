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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comment")
public class CommentModel extends Model {

    private static final long  serialVersionUID = 7372820574885171442L;

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
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.PERSIST}, targetEntity = PostModel.class, optional = false)
    private PostModel          post;

    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.PERSIST}, targetEntity = CommentModel.class)
    @JoinTable(name = "comment_answer", joinColumns = @JoinColumn(name = "id_comment", nullable = false, updatable = false), inverseJoinColumns = @JoinColumn(name = "id_answer", nullable = false, updatable = false))
    private List<CommentModel> answers;

    public CommentModel() {
        super();
    }

    @Override
    public String toString() {
        return "Comment [id=" + super.getId() + ", text=" + text + ", created=" + date
                + ", creator=" + creator + "]";
    }

}
