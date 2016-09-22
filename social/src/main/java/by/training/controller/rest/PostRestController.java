package by.training.controller.rest;

import static by.training.constants.MessageConstants.PERMISSIONS_ERROR;
import static by.training.constants.UrlConstants.ID_KEY;
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
import by.training.database.dao.PostDAO;
import by.training.database.dao.TopicDAO;
import by.training.exception.ValidationException;
import by.training.model.PostModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;
import by.training.utility.Validator;

@RestController
@RequestMapping("/posts")
public class PostRestController extends by.training.controller.rest.RestController {

    private PostDAO  postDAO;
    private TopicDAO topicDAO;

    public PostRestController(final PostDAO postDAO, final TopicDAO topicDAO) {
        this.postDAO = postDAO;
        this.topicDAO = topicDAO;
    }

    @RequestMapping(value = "/create/{text}/" + PATH_KEY + "/{parentPostId}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createPost(@PathVariable("text") final String text,
            @PathVariable("path") final String path,
            @PathVariable("parentPostId") final long parentPostId) {
        try {
            Validator.allNotNull(text, parentPostId);

            TopicModel topic = topicDAO.getTopicByPath(path);
            UserModel user = getLoggedUser();

            if (topic.getUsers().contains(user)) {
                PostModel parentPost = null;
                if (parentPostId > 0) {
                    parentPost = postDAO.getPostById(parentPostId);
                }

                PostModel post = postDAO.createPost(text, user, topic, parentPost);
                return new ResponseEntity<Object>(post, HttpStatus.CREATED);
            }
            return new ResponseEntity<Object>(new ErrorMessage(PERMISSIONS_ERROR),
                    HttpStatus.CONFLICT);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/update/{id}/{text}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> updatePost(@PathVariable("id") final long id,
            @PathVariable("text") final String text) {
        try {
            Validator.allNotNull(id, text);

            if (postDAO.getPostById(id).getCreator().getId() == getLoggedUser().getId()) {
                PostModel post = postDAO.updatePost(id, text);
                return new ResponseEntity<Object>(post, HttpStatus.CREATED);
            }
            return new ResponseEntity<Object>(new ErrorMessage(PERMISSIONS_ERROR),
                    HttpStatus.CONFLICT);

        } catch (ValidationException e) {
            return new ResponseEntity<Object>(new ErrorMessage(e.getMessage()),
                    HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/" + ID_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<PostModel> getPostById(@PathVariable("id") final long id) {
        PostModel post = postDAO.getPostById(id);
        return checkEntity(post);
    }

    @RequestMapping(value = "/topic/" + PATH_KEY + "/" + PAGE_KEY
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<PostModel>> getTopicPosts(@PathVariable("path") final String path,
            @PathVariable("page") final int page) {
        List<PostModel> posts = postDAO.getTopicPosts(path, page);
        return checkEntity(posts);
    }

    @RequestMapping(value = "/feed/" + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<PostModel>> getFeedPosts(@PathVariable("page") final int page) {
        List<PostModel> posts = postDAO.getFeedPosts(getLoggedUser().getId(), page);
        return checkEntity(posts);
    }

    @RequestMapping(value = "/topic/" + PATH_KEY + "/page_count"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getTopicPostsPageCount(@PathVariable("path") final String path) {
        long pageCount = postDAO.getTopicPostsPageCount(path);
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/feed/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getFeedPostsPageCount() {
        long pageCount = postDAO.getFeedPostsPageCount(getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

}
