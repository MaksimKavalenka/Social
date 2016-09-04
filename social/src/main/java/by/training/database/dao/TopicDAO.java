package by.training.database.dao;

import java.util.List;

import by.training.exception.ValidationException;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public interface TopicDAO {

    TopicModel createTopic(String name, String urlName, String description, boolean access,
            UserModel creator) throws ValidationException;

    TopicModel getTopicByUrlName(String urlName);

    List<TopicModel> getUserTopics(long idUser, int page);

    boolean checkName(String name);

    boolean checkUrlName(String urlName);

}
