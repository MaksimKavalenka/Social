package by.training.database.dao;

import java.util.List;

import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public interface TopicDAO {

    TopicModel createTopic(String name, String path, String description, boolean access,
            UserModel creator) throws ValidationException;

    TopicModel updateTopic(long id, String name, String path, String description, boolean access)
            throws ValidationException;

    TopicModel getTopicById(long id);

    TopicModel getTopicByPath(String path);

    List<TopicModel> getTopicsByValue(String value, long userId, int page);

    List<TopicModel> getUserTopics(long userId, int page);

    long getTopicsByValuePageCount(String value, long userId);

    long getUserTopicsCount(long userId);

    long getUserTopicsPageCount(long userId);

    void joinTopic(TopicModel topic, UserModel user);

    void leaveTopic(TopicModel topic, UserModel user);

    boolean checkPath(String path);

}
