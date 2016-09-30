package by.training.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.training.database.dao.UserDAO;
import by.training.entity.UserEntity;

@Service("userDetailsServiceSecurity")
public class UserDetailsServiceSecurity implements UserDetailsService {

    private UserDAO userDAO;

    public UserDetailsServiceSecurity(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserEntity loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userDAO.getUserByLogin(username);
    }

}
