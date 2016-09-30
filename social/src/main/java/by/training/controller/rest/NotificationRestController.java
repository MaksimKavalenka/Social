package by.training.controller.rest;

import static by.training.constants.EntityConstants.ElementsCount;
import static by.training.constants.EntityConstants.Sort;
import static by.training.constants.MessageConstants.VALIDATION_ERROR;
import static by.training.constants.UrlConstants.PAGE_KEY;
import static by.training.constants.UrlConstants.PATH_KEY;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.bean.ErrorMessage;
import by.training.database.dao.TopicDAO;
import by.training.database.dao.UserDAO;
import by.training.entity.NotificationEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.repository.NotificationRepository;
import by.training.utility.Validator;

@RestController
@RequestMapping("/notifications")
public class NotificationRestController extends by.training.controller.rest.RestController {

    private NotificationRepository notificationRepository;
    private TopicDAO               topicDAO;
    private UserDAO                userDAO;

    public NotificationRestController(final TopicDAO topicDAO, final UserDAO userDAO) {
        this.topicDAO = topicDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/create/{usersId}" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createNotification(@PathVariable("usersId") final String usersId,
            @PathVariable("path") final String path) {
        if (!Validator.allNotNull(usersId)) {
            return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                    HttpStatus.CONFLICT);
        }

        TopicEntity topic = topicDAO.getTopicByPath(path);
        for (long userId : getIdList(usersId)) {
            UserEntity user = userDAO.getUserById(userId);
            if (!notificationRepository.existsByTopic_PathAndUser_Id(path, userId)
                    && !topic.getUsers().contains(user)) {
                notificationRepository.save(new NotificationEntity(user, getLoggedUser(), topic));
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

        notificationRepository.delete(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = "/user" + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<NotificationEntity>> getUserNotifications(
            @PathVariable("page") final int page) {
        List<NotificationEntity> notifications = notificationRepository.findByUser_Id(
                getLoggedUser().getId(),
                new PageRequest(page, ElementsCount.NOTIFICATION, Sort.NOTIFICATION));
        return checkEntity(notifications);
    }

    @RequestMapping(value = "/user/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getUserNotificationsPageCount() {
        long pageCount = notificationRepository.countByUser_Id(getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

}
