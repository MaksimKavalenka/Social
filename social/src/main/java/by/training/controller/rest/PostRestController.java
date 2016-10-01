package by.training.controller.rest;

import static by.training.constants.CountConstants.MAX_POST_LEVEL;
import static by.training.constants.MessageConstants.LEVEL_ERROR;
import static by.training.constants.MessageConstants.OPERATION_PERMISSIONS_ERROR;
import static by.training.constants.MessageConstants.PAGE_PERMISSIONS_ERROR;
import static by.training.constants.MessageConstants.VALIDATION_ERROR;
import static by.training.constants.UrlConstants.ID_KEY;
import static by.training.constants.UrlConstants.PAGE_KEY;
import static by.training.constants.UrlConstants.PATH_KEY;
import static by.training.constants.UrlConstants.Rest.POSTS_URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.training.bean.ErrorMessage;
import by.training.bean.PostWithCommentsCount;
import by.training.entity.PostEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.jpa.service.dao.PostServiceDAO;
import by.training.jpa.service.dao.TopicServiceDAO;
import by.training.utility.Validator;

@RestController
@RequestMapping(POSTS_URL)
public class PostRestController extends by.training.controller.rest.RestController {

    private PostServiceDAO  postService;
    private TopicServiceDAO topicService;

    public PostRestController(final PostServiceDAO postService,
            final TopicServiceDAO topicService) {
        this.postService = postService;
        this.topicService = topicService;
    }

    @RequestMapping(value = "/create/{text}" + PATH_KEY + "/{parentPostId}"
            + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> createPost(@PathVariable("text") final String text,
            @PathVariable("path") final String path,
            @PathVariable("parentPostId") final long parentPostId) {
        if (!Validator.allNotNull(text, parentPostId)) {
            return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                    HttpStatus.CONFLICT);
        }

        TopicEntity topic = topicService.getTopicByPath(path);
        UserEntity user = getLoggedUser();

        if (!topic.getUsers().contains(user)) {
            return new ResponseEntity<Object>(new ErrorMessage(OPERATION_PERMISSIONS_ERROR),
                    HttpStatus.CONFLICT);
        }

        if ((parentPostId > 0) && (postService.getPostLevel(parentPostId) >= MAX_POST_LEVEL)) {
            return new ResponseEntity<Object>(new ErrorMessage(LEVEL_ERROR), HttpStatus.CONFLICT);
        }

        PostEntity parentPost = null;
        if (parentPostId > 0) {
            parentPost = postService.getPostById(parentPostId);
        }

        PostEntity post = postService.createPost(text, getLoggedUser(), topic, parentPost);
        return new ResponseEntity<Object>(post, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{id}/{text}" + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> updatePost(@PathVariable("id") final long id,
            @PathVariable("text") final String text) {
        if (!Validator.allNotNull(id, text)) {
            return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                    HttpStatus.CONFLICT);
        }

        if (postService.getPostById(id).getCreator().getId() != getLoggedUser().getId()) {
            return new ResponseEntity<Object>(new ErrorMessage(OPERATION_PERMISSIONS_ERROR),
                    HttpStatus.CONFLICT);
        }

        PostEntity post = postService.updatePost(id, text);
        return new ResponseEntity<Object>(post, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}" + PATH_KEY + JSON_EXT, method = RequestMethod.POST)
    public ResponseEntity<Object> deleteNotification(@PathVariable("id") final long id,
            @PathVariable("path") final String path) {
        if (!Validator.allNotNull(id)) {
            return new ResponseEntity<Object>(new ErrorMessage(VALIDATION_ERROR),
                    HttpStatus.CONFLICT);
        }

        if (topicService.getTopicByPath(path).getCreator().getId() != getLoggedUser().getId()) {
            return new ResponseEntity<Object>(new ErrorMessage(OPERATION_PERMISSIONS_ERROR),
                    HttpStatus.CONFLICT);
        }

        postService.deletePost(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = PATH_KEY + ID_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Object> getPostById(@PathVariable("path") final String path,
            @PathVariable("id") final long id) {
        TopicEntity topic = topicService.getTopicByPath(path);
        if (!topic.isAccess() && !topic.getUsers().contains(getLoggedUser())) {
            return new ResponseEntity<Object>(new ErrorMessage(PAGE_PERMISSIONS_ERROR),
                    HttpStatus.CONFLICT);
        }

        PostEntity post = postService.getPostById(id);
        return checkEntity(post);
    }

    @RequestMapping(value = "/topic" + PATH_KEY + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<PostWithCommentsCount>> getTopicPosts(
            @PathVariable("path") final String path, @PathVariable("page") final int page) {
        List<PostEntity> posts = postService.getTopicPosts(path, page);

        if (posts == null) {
            return new ResponseEntity<List<PostWithCommentsCount>>(HttpStatus.NO_CONTENT);
        }

        List<PostWithCommentsCount> postWithCommentsCounts = new ArrayList<>(posts.size());
        for (PostEntity post : posts) {
            long id = post.getId();
            String text = post.getText();
            Date date = post.getDate();
            UserEntity creator = post.getCreator();
            TopicEntity topic = post.getTopic();
            long commentsCount = postService.getPostCommentsCount(id);

            postWithCommentsCounts
                    .add(new PostWithCommentsCount(id, text, date, creator, topic, commentsCount));
        }
        return new ResponseEntity<List<PostWithCommentsCount>>(postWithCommentsCounts,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/feed" + PAGE_KEY + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<List<PostWithCommentsCount>> getFeedPosts(
            @PathVariable("page") final int page) {
        List<PostEntity> posts = postService.getFeedPosts(getLoggedUser().getId(), page);

        if (posts == null) {
            return new ResponseEntity<List<PostWithCommentsCount>>(HttpStatus.NO_CONTENT);
        }

        List<PostWithCommentsCount> postWithCommentsCounts = new ArrayList<>(posts.size());
        for (PostEntity post : posts) {
            long id = post.getId();
            String text = post.getText();
            Date date = post.getDate();
            UserEntity creator = post.getCreator();
            TopicEntity topic = post.getTopic();
            long commentsCount = postService.getPostCommentsCount(id);

            postWithCommentsCounts
                    .add(new PostWithCommentsCount(id, text, date, creator, topic, commentsCount));
        }
        return new ResponseEntity<List<PostWithCommentsCount>>(postWithCommentsCounts,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/topic" + PATH_KEY + "/page_count"
            + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getTopicPostsPageCount(@PathVariable("path") final String path) {
        long pageCount = postService.getTopicPostsPageCount(path);
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/feed/page_count" + JSON_EXT, method = RequestMethod.GET)
    public ResponseEntity<Long> getFeedPostsPageCount() {
        long pageCount = postService.getFeedPostsPageCount(getLoggedUser().getId());
        return new ResponseEntity<Long>(pageCount, HttpStatus.OK);
    }

}
