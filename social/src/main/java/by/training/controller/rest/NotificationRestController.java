package by.training.controller.rest;

import static by.training.constants.UrlConstants.PATH_KEY;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.bean.ErrorMessage;
import by.training.database.dao.NotificationDAO;
import by.training.database.dao.TopicDAO;
import by.training.database.dao.UserDAO;
import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;
import by.training.utility.Validator;

@RestController
@RequestMapping("/notifications")
public class NotificationRestController extends by.training.controller.rest.RestController {

    private NotificationDAO notificationDAO;
    private TopicDAO        topicDAO;
    private UserDAO         userDAO;

    public NotificationRestController(final NotificationDAO notificationDAO,
            final TopicDAO topicDAO, final UserDAO userDAO) {
        this.notificationDAO = notificationDAO;
        this.topicDAO = topicDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/create/{userId}/" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createTopic(@PathVariable("usersId") final String usersId,
            @PathVariable("topicPath") final String topicPath) {
        try {
            Validator.allNotNull(usersId);

            for (String userId : usersId.split(",")) {
                UserModel user = userDAO.getUserById(Long.valueOf(userId));
                TopicModel topic = topicDAO.getTopicByPath(topicPath);
                if (!notificationDAO.isInvited(topic, user)) {
                    notificationDAO.createNotification(user, getLoggedUser(), topic);
                }
            }
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

}
