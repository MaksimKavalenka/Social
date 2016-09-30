package by.training.database.editor;

import static by.training.constants.MessageConstants.TAKEN_PATH_ERROR;
import static by.training.utility.CriteriaHelper.getCountElements;
import static by.training.utility.CriteriaHelper.getSortField;
import static by.training.utility.CriteriaHelper.getSortOrder;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.EntityConstants.Structure;
import by.training.database.dao.TopicDAO;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.exception.ValidationException;

public class TopicDatabaseEditor extends DatabaseEditor implements TopicDAO {

    private static final Class<TopicEntity> clazz = TopicEntity.class;

    public TopicDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public TopicEntity createTopic(final String name, final String path, final String description,
            final boolean access, final UserEntity creator) throws ValidationException {
        if (!checkPath(path)) {
            TopicEntity topic = new TopicEntity(name, path, description, access, creator);
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
    public TopicEntity updateTopic(final long id, final String name, final String path,
            final String description, final boolean access) throws ValidationException {
        TopicEntity topic = getTopicById(id);
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
    public TopicEntity getTopicById(final long id) {
        return getSessionFactory().getCurrentSession().get(clazz, id);
    }

    @Override
    @Transactional
    public TopicEntity getTopicByPath(final String path) {
        return getUniqueResultByCriteria(clazz, Restrictions.eq(Structure.TopicFields.PATH, path));
    }

    @Override
    @Transactional
    public List<TopicEntity> getTopicsByValue(final String value, final long userId,
            final int page) {
        Criteria criteria = getTopicsByValueCriteria(value, userId);
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public List<TopicEntity> getUserTopics(final long userId, final int page) {
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
    public void joinTopic(final TopicEntity topic, final UserEntity user) {
        topic.getUsers().add(user);
        getSessionFactory().getCurrentSession().update(topic);
    }

    @Override
    @Transactional
    public void leaveTopic(final TopicEntity topic, final UserEntity user) {
        topic.getUsers().remove(user);
        getSessionFactory().getCurrentSession().update(topic);
    }

    @Override
    @Transactional
    public boolean checkPath(final String path) {
        return getUniqueResultByCriteria(clazz,
                Restrictions.eq(Structure.TopicFields.PATH, path)) != null;
    }

    private Criteria getTopicsByValueCriteria(final String value, final long userId) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(Structure.RelationFields.USERS, "alias");
        criteria.add(Restrictions.or(Restrictions.eq("alias." + Structure.UserFields.ID, userId),
                Restrictions.eq(Structure.TopicFields.ACCESS, true)));
        criteria.add(Restrictions.ilike(Structure.TopicFields.NAME, "%" + value + "%"));
        return criteria;
    }

    private Criteria getUserTopicsCriteria(final long userId) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(Structure.RelationFields.USERS, "alias");
        criteria.add(Restrictions.eq("alias." + Structure.UserFields.ID, userId));
        return criteria;
    }

}
