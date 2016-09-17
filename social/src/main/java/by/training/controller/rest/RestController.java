package by.training.controller.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import by.training.model.UserModel;

public class RestController {

    public static final String JSON_EXT = ".json";

    public UserModel getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object user = auth.getPrincipal();
        return (UserModel) user;
    }

}
