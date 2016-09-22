package by.training.database.editor;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants.RoleFields;
import by.training.database.dao.RoleDAO;
import by.training.exception.ValidationException;
import by.training.model.RoleModel;

public class RoleDatabaseEditor extends DatabaseEditor implements RoleDAO {

    private static final Class<RoleModel> clazz = RoleModel.class;

    public RoleDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public RoleModel createRole(final String name) {
        RoleModel role = new RoleModel(name);
        getSessionFactory().getCurrentSession().save(role);
        return role;
    }

    @Override
    @Transactional
    public RoleModel getRoleById(final long id) {
        return (RoleModel) getSessionFactory().getCurrentSession().get(clazz, id);
    }

    @Override
    @Transactional
    public RoleModel getRoleByName(final String name) {
        return getUniqueResultByCriteria(clazz, Restrictions.eq(RoleFields.NAME, name));
    }

}
