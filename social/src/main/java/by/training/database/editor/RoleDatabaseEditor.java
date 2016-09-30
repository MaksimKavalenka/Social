package by.training.database.editor;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.EntityConstants.Structure;
import by.training.database.dao.RoleDAO;
import by.training.entity.RoleEntity;
import by.training.exception.ValidationException;

public class RoleDatabaseEditor extends DatabaseEditor implements RoleDAO {

    private static final Class<RoleEntity> clazz = RoleEntity.class;

    public RoleDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public RoleEntity createRole(final String name) {
        RoleEntity role = new RoleEntity(name);
        getSessionFactory().getCurrentSession().save(role);
        return role;
    }

    @Override
    @Transactional
    public RoleEntity getRoleById(final long id) {
        return getSessionFactory().getCurrentSession().get(clazz, id);
    }

    @Override
    @Transactional
    public RoleEntity getRoleByName(final String name) {
        return getUniqueResultByCriteria(clazz, Restrictions.eq(Structure.RoleFields.NAME, name));
    }

}
