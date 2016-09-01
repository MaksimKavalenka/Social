package by.training.database.editor;

import static by.training.constants.ExceptionConstants.AUTHORIZATION_ERROR;
import static by.training.constants.ExceptionConstants.TAKEN_LOGIN_ERROR;
import static by.training.constants.ModelStructureConstants.UserFields;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import by.training.database.dao.UserDAO;
import by.training.exception.ValidationException;
import by.training.model.RoleModel;
import by.training.model.UserModel;

public class UserDatabaseEditor extends DatabaseEditor implements UserDAO {

    public UserDatabaseEditor() {
        super();
    }

    public UserDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public void createUser(final String login, final String password, final RoleModel role)
            throws ValidationException {
        UserModel checkUser;
        UserModel user = new UserModel();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);

        checkUser = getUserByCriteria(Restrictions.eq(UserFields.LOGIN, user.getLogin()));
        if (checkUser == null) {
            sessionFactory.getCurrentSession().save(user);
        } else {
            throw new ValidationException(TAKEN_LOGIN_ERROR);
        }
    }

    @Override
    @Transactional
    public UserModel getUser(final String login, final String password) throws ValidationException {
        UserModel user = getUserByCriteria(Restrictions.eq(UserFields.LOGIN, login),
                Restrictions.eq(UserFields.PASSWORD, password));
        if (user != null) {
            return user;
        } else {
            throw new ValidationException(AUTHORIZATION_ERROR);
        }
    }

    @Override
    @Transactional
    public UserModel getUserById(final long id) {
        return (UserModel) sessionFactory.getCurrentSession().get(UserModel.class, id);
    }

    @Override
    @Transactional
    public UserModel getUserByLogin(final String login) {
        return getUserByCriteria(Restrictions.eq(UserFields.LOGIN, login));
    }

    private UserModel getUserByCriteria(final Criterion... criterions) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserModel.class);
        for (Criterion criterion : criterions) {
            criteria.add(criterion);
        }
        return (UserModel) criteria.uniqueResult();
    }

}
