package by.training.database.editor;

import static by.training.constants.MessageConstants.TAKEN_LOGIN_ERROR;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.constants.ModelStructureConstants.RelationFields;
import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.constants.ModelStructureConstants.UserFields;
import by.training.database.dao.UserDAO;
import by.training.exception.ValidationException;
import by.training.model.UserModel;

public class UserDatabaseEditor extends DatabaseEditor implements UserDAO {

    private static final Class<UserModel> clazz = UserModel.class;

    public UserDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public UserModel createUser(final String login, final String password,
            final Set<GrantedAuthority> roles) throws ValidationException {
        if (!checkLogin(login)) {
            UserModel user = new UserModel(login, password, roles);
            getSessionFactory().getCurrentSession().save(user);
            return user;
        } else {
            throw new ValidationException(TAKEN_LOGIN_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = ValidationException.class)
    public UserModel updateUser(final long id, final String login, final String password)
            throws ValidationException {
        UserModel user = getUserById(id);
        if (!checkLogin(login) || user.getLogin().equals(login)) {
            user.setLogin(login);
            user.setPassword(password);
            getSessionFactory().getCurrentSession().update(user);
            return user;
        } else {
            throw new ValidationException(TAKEN_LOGIN_ERROR);
        }
    }

    @Override
    @Transactional
    public UserModel updateUserPhoto(final long id, final String photo) {
        UserModel user = getUserById(id);
        user.setPhoto(photo);
        getSessionFactory().getCurrentSession().update(user);
        return user;
    }

    @Override
    @Transactional
    public UserModel getUserById(final long id) {
        return (UserModel) getSessionFactory().getCurrentSession().get(clazz, id);
    }

    @Override
    @Transactional
    public UserModel getUserByLogin(final String login) {
        return getUniqueResultByCriteria(clazz, Restrictions.eq(UserFields.LOGIN, login));
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<UserModel> getUsersForInvitation(final String topicPath) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        detachedCriteria.createAlias(RelationFields.TOPICS, "alias");
        detachedCriteria.add(Restrictions.eq("alias." + TopicFields.PATH, topicPath));
        detachedCriteria.setProjection(Projections.property(ModelFields.ID));

        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz);
        criteria.add(Restrictions.or(Restrictions.isEmpty(RelationFields.TOPICS),
                Subqueries.notExists(detachedCriteria)));
        criteria.addOrder(Order.asc(UserFields.LOGIN));
        return criteria.list();
    }

    @Override
    @Transactional
    public boolean checkLogin(final String login) {
        return getUniqueResultByCriteria(clazz, Restrictions.eq(UserFields.LOGIN, login)) != null;
    }

}
