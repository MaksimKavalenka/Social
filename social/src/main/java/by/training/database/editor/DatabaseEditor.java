package by.training.database.editor;

import static by.training.utility.CriteriaHelper.getCountElements;
import static by.training.utility.CriteriaHelper.getSearchField;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.model.Model;

public abstract class DatabaseEditor {

    private SessionFactory sessionFactory;

    public DatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public <T extends Model> T getUniqueResultByCriteria(final Class<T> clazz,
            final Criterion... criterions) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        for (Criterion criterion : criterions) {
            criteria.add(criterion);
        }
        return (T) criteria.uniqueResult();
    }

    public <T extends Model> Criteria getDefaultRelationCriteria(final Class<T> clazz,
            final String relation, final long relationId) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(getSearchField(clazz, relation), "alias");
        criteria.add(Restrictions.eq("alias." + ModelFields.ID, relationId));
        return criteria;
    }

    @SuppressWarnings("unchecked")
    public <T extends Model> List<T> getElements(final Criteria criteria, final Class<T> clazz,
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
