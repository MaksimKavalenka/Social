package by.training.database.dao;

import java.util.List;

import by.training.entity.PostEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;

public interface PostDAO {

    PostEntity createPost(String text, UserEntity creator, TopicEntity topic, PostEntity parentPost);

    PostEntity updatePost(long id, String text);

    void deletePost(long id);

    PostEntity getPostById(long id);

    List<PostEntity> getTopicPosts(String topicPath, int page);

    List<PostEntity> getFeedPosts(long userId, int page);

    long getPostCommentsCount(long id);

    long getTopicPostsPageCount(String topicPath);

    long getFeedPostsPageCount(long userId);

    long getPostLevel(long id);

}
