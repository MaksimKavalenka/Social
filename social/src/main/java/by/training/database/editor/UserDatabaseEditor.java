package by.training.database.editor;

import static by.training.constants.MessageConstants.TAKEN_LOGIN_ERROR;
import static by.training.constants.ModelStructureConstants.UserFields;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants;
import by.training.constants.ModelStructureConstants.RelationFields;
import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.database.dao.UserDAO;
import by.training.exception.ValidationException;
import by.training.model.UserModel;
import by.training.utility.SecureData;

public class UserDatabaseEditor extends DatabaseEditor implements UserDAO {

    public UserDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public UserModel createUser(final String login, final String password,
            final Set<GrantedAuthority> roles) throws ValidationException {
        try {
            if (!checkLogin(login)) {
                UserModel user = new UserModel(login, SecureData.secureBySha(password, login),
                        roles);
                getSessionFactory().getCurrentSession().save(user);
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
        return (UserModel) getSessionFactory().getCurrentSession().get(UserModel.class, id);
    }

    @Override
    @Transactional
    public UserModel getUserByLogin(final String login) {
        return getUniqueResultByCriteria(UserModel.class, Restrictions.eq(UserFields.LOGIN, login));
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<UserModel> getUsersForInvitation(final String topicPath) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(UserModel.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(RelationFields.TOPICS, "alias");
        criteria.add(Restrictions.ne("alias." + TopicFields.PATH, topicPath));
        criteria.addOrder(Order.asc(ModelStructureConstants.UserFields.LOGIN));
        return criteria.list();
    }

    @Override
    @Transactional
    public boolean checkLogin(final String login) {
        return getUniqueResultByCriteria(UserModel.class,
                Restrictions.eq(UserFields.LOGIN, login)) != null;
    }

}
