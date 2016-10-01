package by.training.jpa.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import by.training.entity.RoleEntity;
import by.training.jpa.repository.RoleRepository;
import by.training.jpa.service.dao.RoleServiceDAO;

public class RoleService implements RoleServiceDAO {

    @Autowired
    private RoleRepository repository;

    @Override
    public RoleEntity createRole(final String name) {
        RoleEntity role = new RoleEntity(name);
        return repository.save(role);
    }

    @Override
    public RoleEntity getRoleById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public RoleEntity getRoleByName(final String name) {
        return repository.findByName(name);
    }

}
