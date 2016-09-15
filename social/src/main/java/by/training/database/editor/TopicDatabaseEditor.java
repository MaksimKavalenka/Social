package by.training.database.editor;

import static by.training.constants.ExceptionConstants.TAKEN_PATH_ERROR;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.database.dao.TopicDAO;
import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public class TopicDatabaseEditor extends DatabaseEditor implements TopicDAO {

    public TopicDatabaseEditor() {
        super();
    }

    public TopicDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public TopicModel createTopic(final String name, final String path, final String description,
            final boolean access, final UserModel creator) throws ValidationException {
        if (!checkPath(path)) {
            TopicModel topic = new TopicModel(name, path, description, access, creator);
            if (path == null) {
                topic.setPath(String.valueOf(topic.getId()));
            }
            sessionFactory.getCurrentSession().save(topic);
            return topic;
        } else {
            throw new ValidationException(TAKEN_PATH_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public TopicModel updateTopic(final long id, final String name, final String path,
            final String description, final boolean access) throws ValidationException {
        if (!checkPath(path)) {
            TopicModel topic = getTopicById(id);
            topic.setName(name);
            topic.setPath(path);
            topic.setDescription(description);
            topic.setAccess(access);
            if (path == null) {
                topic.setPath(String.valueOf(topic.getId()));
            }
            sessionFactory.getCurrentSession().update(topic);
            return topic;
        } else {
            throw new ValidationException(TAKEN_PATH_ERROR);
        }
    }

    @Override
    @Transactional
    public TopicModel getTopicById(final long id) {
        return (TopicModel) sessionFactory.getCurrentSession().get(TopicModel.class, id);
    }

    @Override
    @Transactional
    public TopicModel getTopicByPath(final String path) {
        return getUniqueResultByCriteria(TopicModel.class, Restrictions.eq(TopicFields.PATH, path));
    }

    @Override
    @Transactional
    public void joinTopic(final TopicModel topic, final UserModel user) {
        topic.getUsers().add(user);
        sessionFactory.getCurrentSession().update(topic);
    }

    @Override
    @Transactional
    public void leaveTopic(final TopicModel topic, final UserModel user) {
        topic.getUsers().remove(user);
        sessionFactory.getCurrentSession().update(topic);
    }

    @Override
    @Transactional
    public boolean checkPath(final String path) {
        return getUniqueResultByCriteria(TopicModel.class,
                Restrictions.eq(TopicFields.PATH, path)) != null;
    }

}
