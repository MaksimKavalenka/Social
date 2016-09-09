package by.training.database.dao;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import by.training.exception.ValidationException;
import by.training.model.UserModel;

public interface UserDAO {

    UserModel createUser(String login, String password, List<GrantedAuthority> roles)
            throws ValidationException;

    UserModel getUserById(long id);

    UserModel getUserByLogin(String login);

    UserModel authentication(String login, String password) throws ValidationException;

    boolean checkLogin(String login);

}
