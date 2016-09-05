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

import by.training.model.PostModel;
import by.training.model.TopicModel;

@RestController
public class PostRestController extends by.training.controller.rest.RestController {

    @RequestMapping(value = POSTS_PATH + "/topic/{urlName}/{page}"
            + JSON_EXT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostModel>> getPostsByCriteria(
            @PathVariable("urlName") final String urlName, @PathVariable("page") final int page) {
        TopicModel topic = topicDAO.getTopicByUrlName(urlName);
        List<PostModel> posts = postDAO.getTopicPosts(topic.getId(), page);
        if (posts == null) {
            return new ResponseEntity<List<PostModel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<PostModel>>(posts, HttpStatus.OK);
    }

}
