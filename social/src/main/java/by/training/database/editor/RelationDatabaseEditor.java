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
        return getElements(clazz, getSearchField(clazz, relation), getSortField(clazz), id,
                getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public List<PostModel> getPosts(final long id, final int page) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PostModel.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria criteria1 = sessionFactory.getCurrentSession().createCriteria(TopicModel.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria1.createAlias("users", "alias");
        criteria1.add(Restrictions.eq("alias" + "." + ModelFields.ID, id));
        criteria.add(Restrictions.in("topic", criteria1.list()));
        return getElements(criteria, PostModel.class, getSortField(PostModel.class),
                getSortOrder(PostModel.class), page);
    }

    private <T extends Model> List<T> getElements(final Class<T> clazz, final String searchProperty,
            final String sortProperty, final long id, final boolean order, final int page) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(searchProperty, "alias");
        criteria.add(Restrictions.eq("alias" + "." + ModelFields.ID, id));
        return getElements(criteria, clazz, sortProperty, order, page);
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
