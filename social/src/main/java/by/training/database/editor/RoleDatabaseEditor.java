package by.training.database.editor;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import by.training.database.dao.RoleDAO;
import by.training.model.RoleModel;

public class RoleDatabaseEditor extends DatabaseEditor implements RoleDAO {

    public RoleDatabaseEditor() {
        super();
    }

    public RoleDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public RoleModel getRoleById(final long id) {
        return (RoleModel) sessionFactory.getCurrentSession().get(RoleModel.class, id);
    }

}
