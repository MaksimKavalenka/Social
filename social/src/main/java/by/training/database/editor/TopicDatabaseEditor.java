package by.training.database.editor;

import static by.training.constants.MessageConstants.TAKEN_PATH_ERROR;
import static by.training.utility.CriteriaHelper.getCountElements;
import static by.training.utility.CriteriaHelper.getSearchField;
import static by.training.utility.CriteriaHelper.getSortField;
import static by.training.utility.CriteriaHelper.getSortOrder;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.constants.ModelStructureConstants.Models;
import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.database.dao.TopicDAO;
import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public class TopicDatabaseEditor extends DatabaseEditor implements TopicDAO {

    private static final Class<TopicModel> clazz = TopicModel.class;

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
            getSessionFactory().getCurrentSession().save(topic);
            return topic;
        } else {
            throw new ValidationException(TAKEN_PATH_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public TopicModel updateTopic(final long id, final String name, final String path,
            final String description, final boolean access) throws ValidationException {
        TopicModel topic = getTopicById(id);
        if (!checkPath(path) || topic.getPath().equals(path)) {
            topic.setName(name);
            topic.setPath(path);
            topic.setDescription(description);
            topic.setAccess(access);
            if (path == null) {
                topic.setPath(String.valueOf(topic.getId()));
            }
            getSessionFactory().getCurrentSession().update(topic);
            return topic;
        } else {
            throw new ValidationException(TAKEN_PATH_ERROR);
        }
    }

    @Override
    @Transactional
    public TopicModel getTopicById(final long id) {
        return (TopicModel) getSessionFactory().getCurrentSession().get(clazz, id);
    }

    @Override
    @Transactional
    public TopicModel getTopicByPath(final String path) {
        return getUniqueResultByCriteria(clazz, Restrictions.eq(TopicFields.PATH, path));
    }

    @Override
    @Transactional
    public List<TopicModel> getTopicsByValue(final String value, final long userId,
            final int page) {
        Criteria criteria = getTopicsByValueCriteria(value, userId);
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public List<TopicModel> getUserTopics(final long userId, final int page) {
        Criteria criteria = getUserTopicsCriteria(userId);
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public long getTopicsByValuePageCount(final String value, final long userId) {
        Criteria criteria = getTopicsByValueCriteria(value, userId);
        return (long) Math.ceil((long) criteria.setProjection(Projections.rowCount()).uniqueResult()
                / (double) getCountElements(clazz));
    }

    @Override
    @Transactional
    public long getUserTopicsCount(final long userId) {
        Criteria criteria = getUserTopicsCriteria(userId);
        return (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    @Transactional
    public long getUserTopicsPageCount(final long userId) {
        Criteria criteria = getUserTopicsCriteria(userId);
        return (long) Math.ceil((long) criteria.setProjection(Projections.rowCount()).uniqueResult()
                / (double) getCountElements(clazz));
    }

    @Override
    @Transactional
    public void joinTopic(final TopicModel topic, final UserModel user) {
        topic.getUsers().add(user);
        getSessionFactory().getCurrentSession().update(topic);
    }

    @Override
    @Transactional
    public void leaveTopic(final TopicModel topic, final UserModel user) {
        topic.getUsers().remove(user);
        getSessionFactory().getCurrentSession().update(topic);
    }

    @Override
    @Transactional
    public boolean checkPath(final String path) {
        return getUniqueResultByCriteria(clazz, Restrictions.eq(TopicFields.PATH, path)) != null;
    }

    private Criteria getTopicsByValueCriteria(final String value, final long userId) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        criteria.createAlias(getSearchField(clazz, Models.USER), "alias");
        criteria.add(Restrictions.or(Restrictions.eq("alias." + ModelFields.ID, userId),
                Restrictions.eq(TopicFields.ACCESS, true)));
        criteria.add(Restrictions.ilike(TopicFields.NAME, "%" + value + "%"));
        return criteria;
    }

    private Criteria getUserTopicsCriteria(final long userId) {
        return getDefaultRelationCriteria(clazz, Models.USER, userId);
    }

}
