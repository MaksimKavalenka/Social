package by.training.database.editor;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;

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

}
