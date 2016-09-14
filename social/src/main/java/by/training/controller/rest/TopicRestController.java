package by.training.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.constants.ModelStructureConstants.Models;
import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;

@RestController
@RequestMapping("/topics")
public class TopicRestController extends by.training.controller.rest.RestController {

    @RequestMapping(value = "/create/{name}/{path}/{description}/{access}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopicModel> createTopic(@PathVariable("name") final String name,
            @PathVariable("path") final String path,
            @PathVariable("description") final String description,
            @PathVariable("access") final boolean access) {
        try {
            UserModel creator = getLoggedUser();
            TopicModel topic = topicDAO.createTopic(name, path, description, access, creator);
            return new ResponseEntity<TopicModel>(topic, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<TopicModel>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{path}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopicModel> getTopicByUrlName(@PathVariable("path") final String path) {
        TopicModel topic = topicDAO.getTopicByPath(path);
        if (topic == null) {
            return new ResponseEntity<TopicModel>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<TopicModel>(topic, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TopicModel>> getUserTopics(@PathVariable("page") final int page) {
        List<TopicModel> topics = relationDAO.getElementsByCriteria(TopicModel.class, Models.USER,
                getLoggedUser().getId(), page);
        if (topics == null) {
            return new ResponseEntity<List<TopicModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TopicModel>>(topics, HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{value}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TopicModel>> getTopicsByValue(
            @PathVariable("value") final String value, @PathVariable("page") final int page) {
        List<TopicModel> topics = relationDAO.getTopicsByValue(value, getLoggedUser().getId(),
                page);
        if (topics == null) {
            return new ResponseEntity<List<TopicModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TopicModel>>(topics, HttpStatus.OK);
    }

    @RequestMapping(value = "/join/{path}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> joinTopic(@PathVariable("path") final String path) {
        TopicModel topic = topicDAO.getTopicByPath(path);
        UserModel user = getLoggedUser();
        if (topic.isAccess() || notificationDAO.isInvited(topic.getId(), user.getId())) {
            topicDAO.joinTopic(topic, user);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/leave/{path}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> leaveTopic(@PathVariable("path") final String path) {
        TopicModel topic = topicDAO.getTopicByPath(path);
        UserModel user = getLoggedUser();
        topicDAO.leaveTopic(topic, user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/check_path/{path}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkPath(@PathVariable("path") final String path) {
        boolean exists = topicDAO.checkPath(path);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

    @RequestMapping(value = "/check_member/{path}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkMember(@PathVariable("path") final String topicPath) {
        TopicModel topic = topicDAO.getTopicByPath(topicPath);
        UserModel user = getLoggedUser();
        boolean exists = user.getTopics().contains(topic);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
