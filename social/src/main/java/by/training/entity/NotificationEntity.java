package by.training.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "notification")
public class NotificationEntity extends AbstractEntity {

    private static final long serialVersionUID = -8131266384051642285L;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date              date;

    @JsonIgnore
    @ManyToOne(targetEntity = UserEntity.class, cascade = {
            CascadeType.DETACH}, fetch = FetchType.LAZY, optional = false)
    private UserEntity        user;

    @ManyToOne(targetEntity = UserEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private UserEntity        inviter;

    @ManyToOne(targetEntity = TopicEntity.class, cascade = {CascadeType.DETACH}, optional = false)
    private TopicEntity       topic;

    public NotificationEntity() {
        super();
    }

    public NotificationEntity(final UserEntity user, final UserEntity inviter,
            final TopicEntity topic) {
        super();
        date = new Date();
        this.user = user;
        this.inviter = inviter;
        this.topic = topic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    public UserEntity getInviter() {
        return inviter;
    }

    public void setInviter(final UserEntity inviter) {
        this.inviter = inviter;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(final TopicEntity topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "NotificationEntity [id=" + super.getId() + ", date=" + date + ", inviter=" + inviter
                + ", topic=" + topic + "]";
    }

}
