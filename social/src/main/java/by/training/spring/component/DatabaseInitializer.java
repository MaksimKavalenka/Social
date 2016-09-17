package by.training.spring.component;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.RoleConstants;
import by.training.database.dao.RoleDAO;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private RoleDAO roleDAO;

    public DatabaseInitializer(final RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        roleInit();
    }

    @Transactional
    private void roleInit() {
        for (RoleConstants role : RoleConstants.values()) {
            if (roleDAO.getRoleByName(role.toString()) == null) {
                roleDAO.createRole(role.toString());
            }
        }
    }

}
