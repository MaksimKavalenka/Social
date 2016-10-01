package by.training.jpa.service.dao;

import by.training.entity.RoleEntity;

public interface RoleServiceDAO {

    RoleEntity createRole(String name);

    RoleEntity getRoleById(long id);

    RoleEntity getRoleByName(String name);

}
