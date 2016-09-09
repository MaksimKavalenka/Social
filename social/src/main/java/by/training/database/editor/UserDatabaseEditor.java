package by.training.database.editor;

import static by.training.constants.ExceptionConstants.TAKEN_LOGIN_ERROR;
import static by.training.constants.ModelStructureConstants.UserFields;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import by.training.database.dao.UserDAO;
import by.training.exception.ValidationException;
import by.training.model.UserModel;
import by.training.utility.SecureData;

public class UserDatabaseEditor extends DatabaseEditor implements UserDAO {

    public UserDatabaseEditor() {
        super();
    }

    public UserDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public UserModel createUser(final String login, final String password,
            final Set<GrantedAuthority> roles) throws ValidationException {
        try {
            UserModel checkUserLogin = getUniqueResultByCriteria(UserModel.class,
                    Restrictions.eq(UserFields.LOGIN, login));
            if (checkUserLogin == null) {
                UserModel user = new UserModel(login, SecureData.secureBySha(password, login),
                        roles);
                sessionFactory.getCurrentSession().save(user);
                return user;
            } else {
                throw new ValidationException(TAKEN_LOGIN_ERROR);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new ValidationException(e.getMessage());
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
        return getUniqueResultByCriteria(UserModel.class, Restrictions.eq(UserFields.LOGIN, login));
    }

    @Override
    @Transactional
    public boolean checkLogin(final String login) {
        return getUniqueResultByCriteria(UserModel.class,
                Restrictions.eq(UserFields.LOGIN, login)) != null;
    }

}
