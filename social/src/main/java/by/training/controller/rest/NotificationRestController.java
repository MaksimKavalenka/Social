package by.training.controller.rest;

import static by.training.constants.UrlConstants.PAGE_KEY;
import static by.training.constants.UrlConstants.PATH_KEY;

import java.util.List;

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
import by.training.model.NotificationModel;
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

    @RequestMapping(value = "/create/{usersId}" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createNotification(@PathVariable("usersId") final String usersId,
            @PathVariable("path") final String path) {
        try {
            Validator.allNotNull(usersId);

            TopicModel topic = topicDAO.getTopicByPath(path);
            for (long userId : getIdList(usersId)) {
                UserModel user = userDAO.getUserById(userId);
                if (!notificationDAO.isInvited(topic, user) && !topic.getUsers().contains(user)) {
                    notificationDAO.createNotification(user, getLoggedUser(), topic);
                }
            }
            return new ResponseEntity<Object>(HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/delete/{id}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> deleteNotification(@PathVariable("id") final long id) {
        try {
            Validator.allNotNull(id);

            notificationDAO.deleteNotification(id);
            return new ResponseEntity<Object>(HttpStatus.OK);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/user" + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<NotificationModel>> getUserNotifications(
            @PathVariable("page") final int page) {
        List<NotificationModel> notifications = notificationDAO
                .getUserNotifications(getLoggedUser().getId(), page);
        return checkEntity(notifications);
    }

    @RequestMapping(value = "/user/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getUserNotificationsPageCount() {
        long pageCount = notificationDAO.getUserNotificationsPageCount(getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

}
