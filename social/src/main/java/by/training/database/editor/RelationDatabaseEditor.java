package by.training.database.editor;

import static by.training.utility.CriteriaHelper.*;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.constants.ModelStructureConstants.Models;
import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.database.dao.RelationDAO;
import by.training.model.Model;
import by.training.model.PostModel;
import by.training.model.TopicModel;

public class RelationDatabaseEditor extends DatabaseEditor implements RelationDAO {

    private CriteriaEdit criteriaEdit;

    public RelationDatabaseEditor() {
    }

    public RelationDatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        criteriaEdit = new CriteriaEdit();
    }

    private class CriteriaEdit {

        public <T extends Model> Criteria getCriteriaForElements(final Class<T> clazz,
                final String relation, final long id) {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            criteria.createAlias(getSearchField(clazz, relation), "alias");
            criteria.add(Restrictions.eq("alias." + ModelFields.ID, id));
            return criteria;
        }

        public Criteria getCriteriaForFeedPosts(final long userId) {
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
            return postCriteria;
        }

        public Criteria getCriteriaForTopicsByValue(final String value, final long userId) {
            Class<TopicModel> clazz = TopicModel.class;

            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            criteria.createAlias(getSearchField(clazz, Models.USER), "alias");
            criteria.add(Restrictions.or(Restrictions.eq("alias." + ModelFields.ID, userId),
                    Restrictions.eq(TopicFields.ACCESS, true)));
            criteria.add(Restrictions.ilike(TopicFields.NAME, "%" + value + "%"));
            return criteria;
        }

    }

    @Override
    @Transactional
    public <T extends Model> List<T> getElementsByCriteria(final Class<T> clazz,
            final String relation, final long id, final int page) {
        Criteria criteria = criteriaEdit.getCriteriaForElements(clazz, relation, id);
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public List<PostModel> getFeedPosts(final long userId, final int page) {
        Class<PostModel> postClass = PostModel.class;
        Criteria criteria = criteriaEdit.getCriteriaForFeedPosts(userId);
        return (criteria != null) ? getElements(criteria, postClass, getSortField(postClass),
                getSortOrder(postClass), page) : null;
    }

    @Override
    @Transactional
    public List<TopicModel> getTopicsByValue(final String value, final long userId,
            final int page) {
        Class<TopicModel> clazz = TopicModel.class;
        Criteria criteria = criteriaEdit.getCriteriaForTopicsByValue(value, userId);
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public <T extends Model> long getElementsByCriteriaCount(final Class<T> clazz,
            final String relation, final long id) {
        Criteria criteria = criteriaEdit.getCriteriaForElements(clazz, relation, id);
        return (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    @Transactional
    public <T extends Model> long getElementsByCriteriaPageCount(final Class<T> clazz,
            final String relation, final long id) {
        Criteria criteria = criteriaEdit.getCriteriaForElements(clazz, relation, id);
        return (long) Math.ceil((long) criteria.setProjection(Projections.rowCount()).uniqueResult()
                / (double) getCountElements(clazz));
    }

    @Override
    @Transactional
    public long getFeedPostsPageCount(final long userId) {
        Criteria criteria = criteriaEdit.getCriteriaForFeedPosts(userId);
        return (criteria != null) ? (long) Math
                .ceil((long) criteria.setProjection(Projections.rowCount()).uniqueResult()
                        / (double) getCountElements(PostModel.class))
                : 0;
    }

    @Override
    @Transactional
    public long getTopicsByValuePageCount(final String value, final long userId) {
        Criteria criteria = criteriaEdit.getCriteriaForTopicsByValue(value, userId);
        return (long) Math.ceil((long) criteria.setProjection(Projections.rowCount()).uniqueResult()
                / (double) getCountElements(TopicModel.class));
    }

    @SuppressWarnings("unchecked")
    private <T extends Model> List<T> getElements(final Criteria criteria, final Class<T> clazz,
            final String property, final boolean order, final int page) {
        int count = getCountElements(clazz);
        int fromIndex = count * page - count;

        if (order) {
            criteria.addOrder(Order.asc(property));
        } else {
            criteria.addOrder(Order.desc(property));
        }

        criteria.setFirstResult(fromIndex);
        criteria.setMaxResults(count);
        return criteria.list();
    }

}
