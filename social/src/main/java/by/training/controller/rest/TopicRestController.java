package by.training.controller.rest;

import static by.training.constants.MessageConstants.OPERATION_PERMISSIONS_ERROR;
import static by.training.constants.MessageConstants.VALIDATION_ERROR;
import static by.training.constants.UrlConstants.PAGE_KEY;
import static by.training.constants.UrlConstants.PATH_KEY;
import static by.training.constants.UrlConstants.VALUE_KEY;
import static by.training.constants.UrlConstants.Rest.TOPICS_URL;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.bean.ErrorMessage;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.exception.ValidationException;
import by.training.jpa.service.dao.NotificationServiceDAO;
import by.training.jpa.service.dao.TopicServiceDAO;
import by.training.utility.Validator;

@RestController
@RequestMapping(TOPICS_URL)
public class TopicRestController extends by.training.controller.rest.RestController {

    private NotificationServiceDAO notificationService;
    private TopicServiceDAO        topicService;

    public TopicRestController(final NotificationServiceDAO notificationService,
            final TopicServiceDAO topicService) {
        this.notificationService = notificationService;
        this.topicService = topicService;
    }

    @RequestMapping(value = "/create/{name}" + PATH_KEY + "/{description}/{access}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createTopic(@PathVariable("name") final String name,
            @PathVariable("path") final String path,
            @PathVariable("description") final String description,
            @PathVariable("access") final boolean access) {
        try {
            if (!Validator.allNotNull(name, description, access)) {
                return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                        HttpStatus.CONFLICT);
            }

            UserEntity creator = getLoggedUser();
            TopicEntity topic = topicService.createTopic(name, path, description, access, creator);
            return new ResponseEntity<Object>(topic, HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/update/{id}/{name}" + PATH_KEY + "/{description}/{access}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> updateTopic(@PathVariable("id") final long id,
            @PathVariable("name") final String name, @PathVariable("path") final String path,
            @PathVariable("description") final String description,
            @PathVariable("access") final boolean access) {
        try {
            if (!Validator.allNotNull(id, name, description, access)) {
                return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                        HttpStatus.CONFLICT);
            }

            if (topicService.getTopicById(id).getCreator().getId() != getLoggedUser().getId()) {
                return new ResponseEntity<Object>(new ErrorMessage(OPERATION_PERMISSIONS_ERROR),
                        HttpStatus.CONFLICT);
            }

            TopicEntity topic = topicService.updateTopic(id, name, path, description, access);
            return new ResponseEntity<Object>(topic, HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = PATH_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Object> getTopicByPath(@PathVariable("path") final String path) {
        TopicEntity topic = topicService.getTopicByPath(path);
        return checkEntity(topic);
    }

    @RequestMapping(value = "/user" + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TopicEntity>> getUserTopics(@PathVariable("page") final int page) {
        List<TopicEntity> topics = topicService.getUserTopics(getLoggedUser().getId(), page);
        return checkEntity(topics);
    }

    @RequestMapping(value = "/search" + VALUE_KEY + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TopicEntity>> getTopicsByValue(
            @PathVariable("value") final String value, @PathVariable("page") final int page) {
        List<TopicEntity> topics = topicService.getTopicsByValue(value, getLoggedUser().getId(),
                page);
        return checkEntity(topics);
    }

    @RequestMapping(value = "/user/count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getUserTopicsCount() {
        long pageCount = topicService.getUserTopicsCount(getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getUserTopicsPageCount() {
        long pageCount = topicService.getUserTopicsPageCount(getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/search" + VALUE_KEY + "/page_count"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getTopicsByValuePageCount(
            @PathVariable("value") final String value) {
        long pageCount = topicService.getTopicsByValuePageCount(value, getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/join" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> joinTopic(@PathVariable("path") final String path) {
        long userId = getLoggedUser().getId();
        if (topicService.isPublic(path) || notificationService.isInvited(path, userId)) {
            topicService.joinTopic(path, userId);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/leave" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> leaveTopic(@PathVariable("path") final String path) {
        topicService.leaveTopic(path, getLoggedUser().getId());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/check_path" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkPath(@PathVariable("path") final String path) {
        boolean exists = topicService.checkPath(path);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

    @RequestMapping(value = "/check_member" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkMember(@PathVariable("path") final String path) {
        boolean exists = topicService.isMember(path, getLoggedUser().getId());
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
