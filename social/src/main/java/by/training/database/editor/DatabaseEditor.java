package by.training.database.editor;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import by.training.constants.CountElementConstants;
import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.model.Model;

public abstract class DatabaseEditor {

    @Autowired
    protected SessionFactory sessionFactory;

    public DatabaseEditor() {
    }

    public DatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

    public <T extends Model> List<T> getElementsByCriteria(final Class<T> clazz,
            final String property, final boolean order, final int page) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return getElements(criteria, clazz, property, order, page);
    }

    public <T extends Model> List<T> getElementsByCriteria(final Class<T> clazz,
            final String searchProperty, final String sortProperty, final long id,
            final boolean order, final int page) {
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

        if (page == 0) {
            fromIndex = 0;
            toIndex = count;
        }
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
