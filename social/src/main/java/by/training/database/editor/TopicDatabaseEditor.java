package by.training.database.editor;

import static by.training.constants.ExceptionConstants.TAKEN_NAME_OR_URL_NAME_ERROR;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants.RelationFields;
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
        TopicModel checkTopicName = getUniqueResultByCriteria(TopicModel.class,
                Restrictions.eq(TopicFields.NAME, name));
        TopicModel checkTopicUrlName = getUniqueResultByCriteria(TopicModel.class,
                Restrictions.eq(TopicFields.PATH, path));
        if ((checkTopicName == null) || (checkTopicUrlName == null)) {
            TopicModel topic = new TopicModel();
            topic.setName(name);
            topic.setDescription(description);
            topic.setAccess(access);
            topic.setCreator(creator);
            topic.setUsers(new LinkedList<UserModel>());
            topic.getUsers().add(creator);
            if (!"null".equals(path)) {
                topic.setPath(path);
            } else {
                topic.setPath(String.valueOf(topic.getId()));
            }
            sessionFactory.getCurrentSession().save(topic);
            return topic;
        } else {
            throw new ValidationException(TAKEN_NAME_OR_URL_NAME_ERROR);
        }
    }

    @Override
    @Transactional
    public TopicModel getTopicByPath(final String path) {
        return getUniqueResultByCriteria(TopicModel.class, Restrictions.eq(TopicFields.PATH, path));
    }

    @Override
    @Transactional
    public List<TopicModel> getUserTopics(final long idUser, final int page) {
        return super.getElementsByCriteria(TopicModel.class, RelationFields.USERS, TopicFields.NAME,
                idUser, true, page);
    }

    @Override
    @Transactional
    public boolean checkPath(final String path) {
        return getUniqueResultByCriteria(TopicModel.class,
                Restrictions.eq(TopicFields.PATH, path)) != null;
    }

}
