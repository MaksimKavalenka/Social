package by.training.controller.rest;

import static by.training.constants.RestConstants.JSON_EXT;
import static by.training.constants.RestConstants.TOPICS_PATH;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;

@RestController
public class TopicRestController extends by.training.controller.rest.RestController {

    @RequestMapping(value = TOPICS_PATH
            + "/create/{name}/{urlName}/{description}/{access}/{creatorId}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<TopicModel> createUser(@PathVariable("name") final String name,
            @PathVariable("urlName") final String urlName,
            @PathVariable("description") final String description,
            @PathVariable("access") final boolean access,
            @PathVariable("creatorId") final long creatorId) {
        try {
            UserModel creator = userDAO.getUserById(creatorId);
            TopicModel topic = topicDAO.createTopic(name, urlName, description, access, creator);
            return new ResponseEntity<TopicModel>(topic, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<TopicModel>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = TOPICS_PATH + "/{urlName}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopicModel> getTopicByUrlName(
            @PathVariable("urlName") final String urlName) {
        TopicModel topic = topicDAO.getTopicByUrlName(urlName);
        return new ResponseEntity<TopicModel>(topic, HttpStatus.OK);
    }

    @RequestMapping(value = TOPICS_PATH + "/user/{id}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TopicModel>> getTopicsByCriteria(@PathVariable("id") final long id,
            @PathVariable("page") final int page) {
        List<TopicModel> topics = topicDAO.getUserTopics(id, page);
        if (topics == null) {
            return new ResponseEntity<List<TopicModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TopicModel>>(topics, HttpStatus.OK);
    }

    @RequestMapping(value = TOPICS_PATH + "/checkName/{name}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkName(@PathVariable("name") final String name) {
        boolean exists = topicDAO.checkName(name);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

    @RequestMapping(value = TOPICS_PATH + "/checkUrlName/{urlName}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkUrlName(@PathVariable("urlName") final String urlName) {
        boolean exists = topicDAO.checkUrlName(urlName);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
