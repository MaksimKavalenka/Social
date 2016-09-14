package by.training.database.editor;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.constants.ModelStructureConstants.UserFields;
import by.training.database.dao.NotificationDAO;
import by.training.model.UserModel;

public class NotificationDatabaseEditor extends DatabaseEditor implements NotificationDAO {

    public NotificationDatabaseEditor() {
    }

    public NotificationDatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean isInvited(final long topicId, final long userId) {
        return getUniqueResultByCriteria(UserModel.class, Restrictions.eq(TopicFields.ID, topicId),
                Restrictions.eq(UserFields.ID, userId)) != null;
    }

}
