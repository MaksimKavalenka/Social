package by.training.controller.rest;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.exception.ValidationException;
import by.training.model.UserModel;

@RestController
@RequestMapping("/users")
public class UserRestController extends by.training.controller.rest.RestController {

    @RequestMapping(value = "/create/{login}/{password}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> createUser(@PathVariable("login") final String login,
            @PathVariable("password") final String password) {
        try {
            List<GrantedAuthority> roles = new LinkedList<>();
            roles.add(roleDAO.getRoleById(1));
            UserModel user = userDAO.createUser(login, password, roles);
            return new ResponseEntity<UserModel>(user, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<UserModel>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/auth"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> authentication(final Principal principial) {
        if (principial != null) {
            if (principial instanceof AbstractAuthenticationToken) {
                UserModel user = (UserModel) ((AbstractAuthenticationToken) principial)
                        .getPrincipal();
                return new ResponseEntity<UserModel>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<UserModel>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/logout" + JSON_EXT, method = RequestMethod.POST)
    public void logout(final HttpServletRequest rq, final HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

    @RequestMapping(value = "/check_login/{login}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkLogin(@PathVariable("login") final String login) {
        boolean exists = userDAO.checkLogin(login);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
