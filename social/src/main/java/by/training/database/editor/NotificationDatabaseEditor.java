package by.training.database.editor;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import by.training.constants.ModelStructureConstants.NotificationFields;
import by.training.database.dao.NotificationDAO;
import by.training.model.NotificationModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public class NotificationDatabaseEditor extends DatabaseEditor implements NotificationDAO {

    public NotificationDatabaseEditor() {
    }

    public NotificationDatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public NotificationModel createNotification(final UserModel user, final UserModel inviter,
            final TopicModel topic) {
        NotificationModel notification = new NotificationModel(user, inviter, topic);
        sessionFactory.getCurrentSession().save(notification);
        return notification;
    }

    @Override
    @Transactional
    public boolean isInvited(final TopicModel topic, final UserModel user) {
        return getUniqueResultByCriteria(NotificationModel.class,
                Restrictions.eq(NotificationFields.TOPIC, topic),
                Restrictions.eq(NotificationFields.USER, user)) != null;
    }

}
