package by.training.jpa.service.dao;

import java.util.List;

import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.exception.ValidationException;

public interface TopicServiceDAO {

    TopicEntity createTopic(String name, String path, String description, boolean access,
            UserEntity creator) throws ValidationException;

    TopicEntity updateTopic(long id, String name, String path, String description, boolean access)
            throws ValidationException;

    TopicEntity getTopicById(long id);

    TopicEntity getTopicByPath(String path);

    List<TopicEntity> getTopicsByValue(String value, long userId, int page);

    List<TopicEntity> getUserTopics(long userId, int page);

    long getTopicsByValuePageCount(String value, long userId);

    long getUserTopicsCount(long userId);

    long getUserTopicsPageCount(long userId);

    void joinTopic(String topicPath, long userId);

    void leaveTopic(String topicPath, long userId);

    boolean isPublic(String path);

    boolean isMember(String topicPath, long userId);

    boolean checkPath(String path);

}
