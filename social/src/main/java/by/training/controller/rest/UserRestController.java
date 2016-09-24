package by.training.controller.rest;

import static by.training.constants.MessageConstants.PASSWORD_ERROR;
import static by.training.constants.MessageConstants.PASSWORDS_ERROR;
import static by.training.constants.UrlConstants.PATH_KEY;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import by.training.bean.ErrorMessage;
import by.training.constants.RoleConstants;
import by.training.database.dao.RoleDAO;
import by.training.database.dao.UserDAO;
import by.training.exception.ValidationException;
import by.training.model.UserModel;
import by.training.utility.SecureData;
import by.training.utility.Validator;

@RestController
@RequestMapping("/users")
public class UserRestController extends by.training.controller.rest.RestController {

    private RoleDAO roleDAO;
    private UserDAO userDAO;

    public UserRestController(final RoleDAO roleDAO, final UserDAO userDAO) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/create/{login}/{password}/{confirmPassword}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@PathVariable("login") final String login,
            @PathVariable("password") final String password,
            @PathVariable("confirmPassword") final String confirmPassword) {
        try {
            Validator.allNotNull(login, password, confirmPassword);

            if (!password.equals(confirmPassword)) {
                return new ResponseEntity<Object>(new ErrorMessage(PASSWORDS_ERROR),
                        HttpStatus.CONFLICT);
            }

            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(roleDAO.getRoleByName(RoleConstants.ROLE_USER.toString()));
            userDAO.createUser(login, SecureData.secureBySha(password, login), roles);
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException | NoSuchAlgorithmException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/update/{login}/{currentPassword}/{password}/{confirmPassword}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> updateUser(@PathVariable("login") final String login,
            @PathVariable("currentPassword") final String currentPassword,
            @PathVariable("password") final String password,
            @PathVariable("confirmPassword") final String confirmPassword) {
        try {
            Validator.allNotNull(login, currentPassword, password, confirmPassword);

            if (!SecureData.secureBySha(currentPassword, login)
                    .equals(getLoggedUser().getPassword())) {
                return new ResponseEntity<Object>(new ErrorMessage(PASSWORD_ERROR),
                        HttpStatus.CONFLICT);
            }
            if (!password.equals(confirmPassword)) {
                return new ResponseEntity<Object>(new ErrorMessage(PASSWORDS_ERROR),
                        HttpStatus.CONFLICT);
            }

            userDAO.updateUser(getLoggedUser().getId(), login,
                    SecureData.secureBySha(password, login));
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException | NoSuchAlgorithmException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/update/{login}/{currentPassword}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> updateUserLogin(@PathVariable("login") final String login,
            @PathVariable("currentPassword") final String currentPassword) {
        try {
            Validator.allNotNull(login, currentPassword);

            if (!SecureData.secureBySha(currentPassword, getLoggedUser().getLogin())
                    .equals(getLoggedUser().getPassword())) {
                return new ResponseEntity<Object>(new ErrorMessage(PASSWORD_ERROR),
                        HttpStatus.CONFLICT);
            }

            userDAO.updateUser(getLoggedUser().getId(), login,
                    SecureData.secureBySha(currentPassword, login));
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException | NoSuchAlgorithmException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/update/{photo}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> updateUserPhoto(@PathVariable("photo") final String photo) {
        try {
            Validator.allNotNull(photo);

            userDAO.updateUserPhoto(getLoggedUser().getId(), photo);
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = PATH_KEY + "/for_invitation" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<List<UserModel>> getUsersForInvitation(
            @PathVariable("path") final String path) {
        List<UserModel> users = userDAO.getUsersForInvitation(path);
        return checkEntity(users);
    }

    @RequestMapping(value = "/auth"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> authentication(final Principal principal) {
        if (principal != null) {
            if (principal instanceof AbstractAuthenticationToken) {
                UserModel user = (UserModel) ((AbstractAuthenticationToken) principal)
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

    @RequestMapping(value = "/check_login/{login}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkLogin(@PathVariable("login") final String login) {
        boolean exists = userDAO.checkLogin(login);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
