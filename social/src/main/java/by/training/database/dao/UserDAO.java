package by.training.database.dao;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import by.training.exception.ValidationException;
import by.training.model.UserModel;

public interface UserDAO {

    UserModel createUser(String login, String password, Set<GrantedAuthority> roles)
            throws ValidationException;

    UserModel updateUser(long id, String login, String password) throws ValidationException;

    UserModel updateUserLogin(long id, String login) throws ValidationException;

    UserModel getUserById(long id);

    UserModel getUserByLogin(String login);

    List<UserModel> getUsersForInvitation(String topicPath);

    boolean checkLogin(String login);

}
