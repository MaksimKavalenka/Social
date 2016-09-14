package by.training.database.editor;

import static by.training.utility.CriteriaHelper.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.CountElementConstants;
import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.constants.ModelStructureConstants.Models;
import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.database.dao.RelationDAO;
import by.training.model.Model;
import by.training.model.PostModel;
import by.training.model.TopicModel;

public class RelationDatabaseEditor extends DatabaseEditor implements RelationDAO {

    public RelationDatabaseEditor() {
    }

    public RelationDatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public <T extends Model> List<T> getElementsByCriteria(final Class<T> clazz,
            final String relation, final long id, final int page) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(getSearchField(clazz, relation), "alias");
        criteria.add(Restrictions.eq("alias." + ModelFields.ID, id));
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public List<PostModel> getFeedPosts(final long userId, final int page) {
        Class<PostModel> postClass = PostModel.class;
        Class<TopicModel> topicClass = TopicModel.class;
        Criteria postCriteria = sessionFactory.getCurrentSession().createCriteria(postClass)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria topicCriteria = sessionFactory.getCurrentSession().createCriteria(topicClass)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        topicCriteria.createAlias(getSearchField(topicClass, Models.USER), "alias");
        topicCriteria.add(Restrictions.eq("alias." + ModelFields.ID, userId));
        if (topicCriteria.list().isEmpty()) {
            return null;
        }
        postCriteria.add(
                Restrictions.in(getSearchField(postClass, Models.TOPIC), topicCriteria.list()));
        return getElements(postCriteria, postClass, getSortField(postClass),
                getSortOrder(postClass), page);
    }

    @Override
    @Transactional
    public List<TopicModel> getTopicsByValue(final String value, final long userId,
            final int page) {
        Class<TopicModel> clazz = TopicModel.class;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(getSearchField(clazz, Models.USER), "alias");
        criteria.add(Restrictions.or(Restrictions.eq("alias." + ModelFields.ID, userId),
                Restrictions.eq(TopicFields.ACCESS, true)));
        criteria.add(Restrictions.ilike(TopicFields.NAME, "%" + value + "%"));
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @SuppressWarnings("unchecked")
    private <T extends Model> List<T> getElements(final Criteria criteria, final Class<T> clazz,
            final String property, final boolean order, final int page) {
        int count = CountElementConstants.COUNT_ELEMENTS;
        int fromIndex = count * page - count;
        int toIndex = count * page;

        if (order) {
            criteria.addOrder(Order.asc(property));
        } else {
            criteria.addOrder(Order.desc(property));
        }
        if (criteria.list().size() >= toIndex) {
            return criteria.list().subList(fromIndex, toIndex);
        }
        if (criteria.list().size() > fromIndex) {
            return criteria.list().subList(fromIndex, criteria.list().size());
        }
        return null;
    }

}
