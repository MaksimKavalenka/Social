package by.training.database.editor;

import static by.training.utility.CriteriaHelper.getCountElements;
import static by.training.utility.CriteriaHelper.getSortField;
import static by.training.utility.CriteriaHelper.getSortOrder;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import by.training.constants.ModelStructureConstants.Models;
import by.training.constants.ModelStructureConstants.NotificationFields;
import by.training.database.dao.NotificationDAO;
import by.training.model.NotificationModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public class NotificationDatabaseEditor extends DatabaseEditor implements NotificationDAO {

    private static final Class<NotificationModel> clazz = NotificationModel.class;

    public NotificationDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public NotificationModel createNotification(final UserModel user, final UserModel inviter,
            final TopicModel topic) {
        NotificationModel notification = new NotificationModel(user, inviter, topic);
        getSessionFactory().getCurrentSession().save(notification);
        return notification;
    }

    @Override
    @Transactional
    public void deleteNotification(final long id) {
        NotificationModel notification = getNotificationById(id);
        getSessionFactory().getCurrentSession().delete(notification);
    }

    @Override
    @Transactional
    public NotificationModel getNotificationById(final long id) {
        return (NotificationModel) getSessionFactory().getCurrentSession().get(clazz, id);
    }

    @Override
    @Transactional
    public List<NotificationModel> getUserNotifications(final long userId, final int page) {
        Criteria criteria = getUserNotificationsCriteria(userId);
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public long getUserNotificationsPageCount(final long userId) {
        Criteria criteria = getUserNotificationsCriteria(userId);
        return (long) Math.ceil((long) criteria.setProjection(Projections.rowCount()).uniqueResult()
                / (double) getCountElements(clazz));
    }

    @Override
    @Transactional
    public boolean isInvited(final TopicModel topic, final UserModel user) {
        return getUniqueResultByCriteria(clazz, Restrictions.eq(NotificationFields.TOPIC, topic),
                Restrictions.eq(NotificationFields.USER, user)) != null;
    }

    private Criteria getUserNotificationsCriteria(final long userId) {
        return getDefaultRelationCriteria(clazz, Models.USER, userId);
    }

}
