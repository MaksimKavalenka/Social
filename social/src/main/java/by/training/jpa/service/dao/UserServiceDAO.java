package by.training.jpa.service.dao;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import by.training.entity.UserEntity;
import by.training.exception.ValidationException;

public interface UserServiceDAO {

    UserEntity createUser(String login, String password, Set<GrantedAuthority> roles)
            throws ValidationException;

    UserEntity updateUser(long id, String login, String password) throws ValidationException;

    UserEntity updateUserPhoto(long id, String photo);

    UserEntity getUserById(long id);

    UserEntity getUserByLogin(String login);

    List<UserEntity> getUsersForInvitation(String topicPath);

    boolean checkLogin(String login);

}
