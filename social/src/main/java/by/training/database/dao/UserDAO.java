package by.training.database.dao;

import java.util.List;

import by.training.exception.ValidationException;
import by.training.model.RoleModel;
import by.training.model.UserModel;

public interface UserDAO {

    UserModel createUser(String login, String password, List<RoleModel> roles)
            throws ValidationException;

    UserModel getUserById(long id);

    UserModel getUserByLogin(String login);

    UserModel authentication(String login, String password) throws ValidationException;

    boolean checkLogin(String login);

}
