package by.training.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import by.training.database.dao.RoleDAO;

@Component
public class DatabaseApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        roleInit();
    }

    @Transactional
    private void roleInit() {
        String[] roles = {"ROLE_USER"};
        for (String role : roles) {
            if (roleDAO.getRoleByName(role) == null) {
                roleDAO.createRole(role);
            }
        }
    }

}
