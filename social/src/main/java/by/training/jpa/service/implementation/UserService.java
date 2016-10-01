package by.training.jpa.service.implementation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import by.training.entity.UserEntity;
import by.training.exception.ValidationException;
import by.training.jpa.repository.UserRepository;
import by.training.jpa.service.dao.UserServiceDAO;

public class UserService implements UserServiceDAO {

    @Autowired
    private UserRepository repository;

    @Override
    public UserEntity createUser(final String login, final String password,
            final Set<GrantedAuthority> roles) throws ValidationException {
        UserEntity user = new UserEntity(login, password, roles);
        return repository.save(user);
    }

    @Override
    public UserEntity updateUser(final long id, final String login, final String password)
            throws ValidationException {
        UserEntity user = getUserById(id);
        user.setLogin(login);
        user.setPassword(password);
        return repository.save(user);
    }

    @Override
    public UserEntity updateUserPhoto(final long id, final String photo) {
        UserEntity user = getUserById(id);
        user.setPhoto(photo);
        return repository.save(user);
    }

    @Override
    public UserEntity getUserById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public UserEntity getUserByLogin(final String login) {
        return repository.findByLogin(login);
    }

    @Override
    public List<UserEntity> getUsersForInvitation(final String topicPath) {
        return repository.findAllByTopicsPathNotLikeOrderByLoginAsc(topicPath);
    }

    @Override
    public boolean checkLogin(final String login) {
        return repository.checkLogin(login);
    }

}
