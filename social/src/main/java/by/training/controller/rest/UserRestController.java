package by.training.controller.rest;

import static by.training.constants.RestConstants.JSON_EXT;
import static by.training.constants.RestConstants.USERS_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.database.dao.RoleDAO;
import by.training.database.dao.UserDAO;
import by.training.exception.ValidationException;
import by.training.model.UserModel;

@RestController
public class UserRestController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;

    @RequestMapping(value = USERS_PATH + "/create/{login}/{password}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@PathVariable("login") final String login,
            @PathVariable("password") final String password) {
        try {
            userDAO.createUser(login, password, roleDAO.getRoleById(1));
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = USERS_PATH + "/{login}/{password}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUser(@PathVariable("login") final String login,
            @PathVariable("password") final String password) {
        try {
            UserModel user = userDAO.getUser(login, password);
            if (user == null) {
                return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<UserModel>(user, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<UserModel>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = USERS_PATH + "/{login}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUserByLogin(@PathVariable("login") final String login) {
        UserModel user = userDAO.getUserByLogin(login);
        if (user == null) {
            return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<UserModel>(user, HttpStatus.OK);
    }

}
