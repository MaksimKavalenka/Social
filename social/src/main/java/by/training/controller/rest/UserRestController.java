package by.training.controller.rest;

import static by.training.constants.RestConstants.JSON_EXT;
import static by.training.constants.RestConstants.USERS_PATH;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.exception.ValidationException;
import by.training.model.UserModel;

@RestController
public class UserRestController extends by.training.controller.rest.RestController {

    @RequestMapping(value = USERS_PATH + "/create/{login}/{password}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<UserModel> createUser(@PathVariable("login") final String login,
            @PathVariable("password") final String password) {
        try {
            UserModel user = userDAO.createUser(login, password, roleDAO.getRoleById(1));
            return new ResponseEntity<UserModel>(user, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<UserModel>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = USERS_PATH + "/auth/{login}/{password}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> authentication(@PathVariable("login") final String login,
            @PathVariable("password") final String password) {
        try {
            UserModel user = userDAO.authentication(login, password);
            if (user == null) {
                return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<UserModel>(user, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<UserModel>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = USERS_PATH + "/checkLogin/{login}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkLogin(@PathVariable("login") final String login) {
        boolean exists = userDAO.checkLogin(login);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
