package by.training.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.training.database.dao.UserDAO;
import by.training.model.UserModel;

@Service
public class UserDetailsServiceSecurity implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserModel loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userDAO.getUserByLogin(username);
    }

}
