package by.training.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notification")
public class NotificationModel extends Model {

    private static final long serialVersionUID = -8131266384051642285L;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date              date;

    @ManyToOne(targetEntity = UserModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    private UserModel         user;

    @ManyToOne(targetEntity = UserModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    private UserModel         inviter;

    @ManyToOne(targetEntity = TopicModel.class, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    private TopicModel        topic;

    public NotificationModel() {
        super();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(final UserModel user) {
        this.user = user;
    }

    public UserModel getInviter() {
        return inviter;
    }

    public void setInviter(final UserModel inviter) {
        this.inviter = inviter;
    }

    public TopicModel getTopic() {
        return topic;
    }

    public void setTopic(final TopicModel topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Notification [id=" + super.getId() + ", date=" + date + ", user=" + user
                + ", inviter=" + inviter + ", topic=" + topic + "]";
    }

}
