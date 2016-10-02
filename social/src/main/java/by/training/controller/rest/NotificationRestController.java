package by.training.controller.rest;

import static by.training.constants.MessageConstants.VALIDATION_ERROR;
import static by.training.constants.UrlConstants.PAGE_KEY;
import static by.training.constants.UrlConstants.PATH_KEY;
import static by.training.constants.UrlConstants.Rest.NOTIFICATIONS_URL;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.bean.ErrorMessage;
import by.training.entity.NotificationEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.jpa.service.dao.NotificationServiceDAO;
import by.training.jpa.service.dao.TopicServiceDAO;
import by.training.jpa.service.dao.UserServiceDAO;
import by.training.utility.Validator;

@RestController
@RequestMapping(NOTIFICATIONS_URL)
public class NotificationRestController extends by.training.controller.rest.RestController {

    private NotificationServiceDAO notificationService;
    private TopicServiceDAO        topicService;
    private UserServiceDAO         userService;

    public NotificationRestController(final NotificationServiceDAO notificationService,
            final TopicServiceDAO topicService, final UserServiceDAO userService) {
        this.notificationService = notificationService;
        this.topicService = topicService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create/{usersId}" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createNotification(@PathVariable("usersId") final String usersId,
            @PathVariable("path") final String path) {
        if (!Validator.allNotNull(usersId)) {
            return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                    HttpStatus.CONFLICT);
        }

        TopicEntity topic = topicService.getTopicByPath(path);
        for (long userId : getIdList(usersId)) {
            UserEntity user = userService.getUserById(userId);
            if (!notificationService.isInvited(path, userId)
                    && !topicService.isMember(path, userId)) {
                notificationService.createNotification(user, getLoggedUser(), topic);
            }
        }
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> deleteNotification(@PathVariable("id") final long id) {
        if (!Validator.allNotNull(id)) {
            return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                    HttpStatus.CONFLICT);
        }

        notificationService.deleteNotification(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user" + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<NotificationEntity>> getUserNotifications(
            @PathVariable("page") final int page) {
        List<NotificationEntity> notifications = notificationService
                .getUserNotifications(getLoggedUser().getId(), page);
        return checkEntity(notifications);
    }

    @RequestMapping(value = "/user/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getUserNotificationsPageCount() {
        long pageCount = notificationService.getUserNotificationsPageCount(getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

}
