package by.training.database.dao;

import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public interface TopicDAO {

    TopicModel createTopic(String name, String path, String description, boolean access,
            UserModel creator) throws ValidationException;

    TopicModel getTopicById(long id);

    TopicModel getTopicByPath(String path);

    boolean checkPath(String path);

}
