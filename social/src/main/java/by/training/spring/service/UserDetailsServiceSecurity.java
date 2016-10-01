package by.training.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.training.entity.UserEntity;
import by.training.jpa.service.dao.UserServiceDAO;

@Service("userDetailsServiceSecurity")
public class UserDetailsServiceSecurity implements UserDetailsService {

    private UserServiceDAO userService;

    public UserDetailsServiceSecurity(final UserServiceDAO userService) {
        this.userService = userService;
    }

    @Override
    public UserEntity loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userService.getUserByLogin(username);
    }

}
