package by.training.controller.rest;

import static by.training.constants.RestConstants.JSON_EXT;
import static by.training.constants.RestConstants.POSTS_PATH;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.constants.ModelStructureConstants.Models;
import by.training.model.PostModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

@RestController
public class PostRestController extends by.training.controller.rest.RestController {

    @RequestMapping(value = POSTS_PATH + "/create/{text}/{creatorId}/{topicId}/{parentPostId}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<PostModel> createUser(@PathVariable("text") final String text,
            @PathVariable("creatorId") final long creatorId,
            @PathVariable("topicId") final long topicId,
            @PathVariable("parentPostId") final long parentPostId) {
        UserModel creator = userDAO.getUserById(creatorId);
        TopicModel topic = topicDAO.getTopicById(topicId);
        PostModel parentPost = null;
        if (parentPostId > 0) {
            postDAO.getPostById(parentPostId);
        }
        PostModel post = postDAO.createPost(text, creator, topic, parentPost);
        return new ResponseEntity<PostModel>(post, HttpStatus.CREATED);
    }

    @RequestMapping(value = POSTS_PATH + "/{relation}/{id}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostModel>> getPostsByCriteria(
            @PathVariable("relation") final String relation, @PathVariable("id") final long id,
            @PathVariable("page") final int page) {
        List<PostModel> posts = relationDAO.getElementsByCriteria(PostModel.class, relation, id,
                page);
        if (posts == null) {
            return new ResponseEntity<List<PostModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PostModel>>(posts, HttpStatus.OK);
    }

    @RequestMapping(value = POSTS_PATH + "/topic/{path}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostModel>> getPostsByCriteria(
            @PathVariable("path") final String path, @PathVariable("page") final int page) {
        TopicModel topic = topicDAO.getTopicByPath(path);
        if (topic == null) {
            return new ResponseEntity<List<PostModel>>(HttpStatus.NO_CONTENT);
        }
        List<PostModel> posts = relationDAO.getElementsByCriteria(PostModel.class, Models.TOPIC,
                topic.getId(), page);
        if (posts == null) {
            return new ResponseEntity<List<PostModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PostModel>>(posts, HttpStatus.OK);
    }

    @RequestMapping(value = POSTS_PATH + "/feed/user/{id}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostModel>> getFeedPosts(@PathVariable("id") final long id,
            @PathVariable("page") final int page) {
        List<PostModel> posts = relationDAO.getPosts(id, page);
        if (posts == null) {
            return new ResponseEntity<List<PostModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PostModel>>(posts, HttpStatus.OK);
    }

}
