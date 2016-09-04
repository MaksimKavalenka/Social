package by.training.database.dao;

import by.training.exception.ValidationException;
import by.training.model.RoleModel;
import by.training.model.UserModel;

public interface UserDAO {

    UserModel createUser(String login, String password, RoleModel role) throws ValidationException;

    UserModel getUserById(long id);

    UserModel authentication(String login, String password) throws ValidationException;

    boolean checkLogin(String login);

}
