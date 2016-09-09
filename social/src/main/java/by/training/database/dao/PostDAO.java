package by.training.database.dao;

import by.training.model.PostModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public interface PostDAO {

    PostModel createPost(String text, UserModel creator, TopicModel topic, PostModel parentPost);

    PostModel getPostById(long id);

}
