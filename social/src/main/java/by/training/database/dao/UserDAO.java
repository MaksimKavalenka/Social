package by.training.database.dao;

import by.training.exception.ValidationException;
import by.training.model.RoleModel;
import by.training.model.UserModel;

public interface UserDAO {

    void createUser(String login, String password, RoleModel role) throws ValidationException;

    UserModel getUser(String login, String password) throws ValidationException;

    UserModel getUserById(long id);

    UserModel getUserByLogin(String login);

}
