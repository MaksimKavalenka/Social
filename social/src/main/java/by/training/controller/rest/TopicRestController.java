package by.training.controller.rest;

import static by.training.constants.MessageConstants.PERMISSIONS_ERROR;
import static by.training.constants.UrlConstants.PAGE_KEY;
import static by.training.constants.UrlConstants.PATH_KEY;
import static by.training.constants.UrlConstants.VALUE_KEY;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.bean.ErrorMessage;
import by.training.constants.ModelStructureConstants.Models;
import by.training.database.dao.NotificationDAO;
import by.training.database.dao.RelationDAO;
import by.training.database.dao.TopicDAO;
import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;
import by.training.utility.Validator;

@RestController
@RequestMapping("/topics")
public class TopicRestController extends by.training.controller.rest.RestController {

    private NotificationDAO notificationDAO;
    private TopicDAO        topicDAO;
    private RelationDAO     relationDAO;

    public TopicRestController(final NotificationDAO notificationDAO, final TopicDAO topicDAO,
            final RelationDAO relationDAO) {
        this.notificationDAO = notificationDAO;
        this.topicDAO = topicDAO;
        this.relationDAO = relationDAO;
    }

    @RequestMapping(value = "/create/{name}/" + PATH_KEY + "/{description}/{access}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createTopic(@PathVariable("name") final String name,
            @PathVariable("path") final String path,
            @PathVariable("description") final String description,
            @PathVariable("access") final boolean access) {
        try {
            Validator.allNotNull(name, description, access);

            UserModel creator = getLoggedUser();
            TopicModel topic = topicDAO.createTopic(name, path, description, access, creator);
            return new ResponseEntity<Object>(topic, HttpStatus.CREATED);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/update/{id}/{name}/" + PATH_KEY + "/{description}/{access}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> updateTopic(@PathVariable("id") final long id,
            @PathVariable("name") final String name, @PathVariable("path") final String path,
            @PathVariable("description") final String description,
            @PathVariable("access") final boolean access) {
        try {
            Validator.allNotNull(id, name, description, access);

            if (topicDAO.getTopicById(id).getCreator().getId() == getLoggedUser().getId()) {
                TopicModel topic = topicDAO.updateTopic(id, name, path, description, access);
                return new ResponseEntity<Object>(topic, HttpStatus.CREATED);
            }
            return new ResponseEntity<Object>(new ErrorMessage(PERMISSIONS_ERROR),
                    HttpStatus.CONFLICT);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/" + PATH_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<TopicModel> getTopicByUrlName(@PathVariable("path") final String path) {
        TopicModel topic = topicDAO.getTopicByPath(path);
        if (topic == null) {
            return new ResponseEntity<TopicModel>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<TopicModel>(topic, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/" + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TopicModel>> getUserTopics(@PathVariable("page") final int page) {
        List<TopicModel> topics = relationDAO.getElementsByCriteria(TopicModel.class, Models.USER,
                getLoggedUser().getId(), page);
        if (topics == null) {
            return new ResponseEntity<List<TopicModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TopicModel>>(topics, HttpStatus.OK);
    }

    @RequestMapping(value = "/search/" + VALUE_KEY + "/" + PAGE_KEY
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<TopicModel>> getTopicsByValue(
            @PathVariable("value") final String value, @PathVariable("page") final int page) {
        List<TopicModel> topics = relationDAO.getTopicsByValue(value, getLoggedUser().getId(),
                page);
        if (topics == null) {
            return new ResponseEntity<List<TopicModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TopicModel>>(topics, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getUserTopicsCount() {
        long pageCount = relationDAO.getElementsByCriteriaCount(TopicModel.class, Models.USER,
                getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getUserTopicsPageCount() {
        long pageCount = relationDAO.getElementsByCriteriaPageCount(TopicModel.class, Models.USER,
                getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/search/" + VALUE_KEY + "/page_count"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getTopicsByValuePageCount(
            @PathVariable("value") final String value) {
        long pageCount = relationDAO.getTopicsByValuePageCount(value, getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/join/" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> joinTopic(@PathVariable("path") final String path) {
        TopicModel topic = topicDAO.getTopicByPath(path);
        UserModel user = getLoggedUser();
        if (topic.isAccess() || notificationDAO.isInvited(topic.getId(), user.getId())) {
            topicDAO.joinTopic(topic, user);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/leave/" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Void> leaveTopic(@PathVariable("path") final String path) {
        TopicModel topic = topicDAO.getTopicByPath(path);
        UserModel user = getLoggedUser();
        topicDAO.leaveTopic(topic, user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/check_path/" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkPath(@PathVariable("path") final String path) {
        boolean exists = topicDAO.checkPath(path);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

    @RequestMapping(value = "/check_member/" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Boolean> checkMember(@PathVariable("path") final String path) {
        TopicModel topic = topicDAO.getTopicByPath(path);
        UserModel user = getLoggedUser();
        boolean exists = topic.getUsers().contains(user);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
