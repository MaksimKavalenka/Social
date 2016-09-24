package by.training.database.dao;

import java.util.List;

import by.training.model.PostModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public interface PostDAO {

    PostModel createPost(String text, UserModel creator, TopicModel topic, PostModel parentPost);

    PostModel updatePost(long id, String text);

    void deletePost(long id);

    PostModel getPostById(long id);

    List<PostModel> getTopicPosts(String topicPath, int page);

    List<PostModel> getFeedPosts(long userId, int page);

    long getTopicPostsPageCount(String topicPath);

    long getFeedPostsPageCount(long userId);

}
