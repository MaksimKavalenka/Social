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
import by.training.model.PostModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

@RestController
@RequestMapping("/posts")
public class PostRestController extends by.training.controller.rest.RestController {

    @RequestMapping(value = "/create/{text}/{topicId}/{parentPostId}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostModel> createPost(@PathVariable("text") final String text,
            @PathVariable("topicId") final long topicId,
            @PathVariable("parentPostId") final long parentPostId) {
        TopicModel topic = topicDAO.getTopicById(topicId);
        UserModel user = getLoggedUser();
        if (topic.getUsers().contains(user)) {
            PostModel parentPost = null;
            if (parentPostId > 0) {
                postDAO.getPostById(parentPostId);
            }
            PostModel post = postDAO.createPost(text, user, topic, parentPost);
            return new ResponseEntity<PostModel>(post, HttpStatus.CREATED);
        }
        return new ResponseEntity<PostModel>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/update/{id}/{text}"
            + JSON_EXT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostModel> updatePost(@PathVariable("id") final long id,
            @PathVariable("text") final String text) {
        if (postDAO.getPostById(id).getCreator().getId() == getLoggedUser().getId()) {
            PostModel post = postDAO.updatePost(id, text);
            return new ResponseEntity<PostModel>(post, HttpStatus.CREATED);
        }
        return new ResponseEntity<PostModel>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/topic/{path}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostModel>> getTopicPosts(@PathVariable("path") final String path,
            @PathVariable("page") final int page) {
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

    @RequestMapping(value = "/feed/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostModel>> getFeedPosts(@PathVariable("page") final int page) {
        List<PostModel> posts = relationDAO.getFeedPosts(getLoggedUser().getId(), page);
        if (posts == null) {
            return new ResponseEntity<List<PostModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PostModel>>(posts, HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostModel> get(@PathVariable("id") final long id) {
        PostModel post = postDAO.getPostById(id);
        return new ResponseEntity<PostModel>(post, HttpStatus.OK);
    }

}
