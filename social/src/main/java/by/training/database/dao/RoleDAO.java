package by.training.database.dao;

import by.training.model.RoleModel;

public interface RoleDAO {

    RoleModel createRole(String name);

    RoleModel getRoleById(long id);

    RoleModel getRoleByName(String name);

}
