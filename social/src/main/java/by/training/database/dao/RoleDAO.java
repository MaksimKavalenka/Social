package by.training.database.dao;

import by.training.entity.RoleEntity;

public interface RoleDAO {

    RoleEntity createRole(String name);

    RoleEntity getRoleById(long id);

    RoleEntity getRoleByName(String name);

}
