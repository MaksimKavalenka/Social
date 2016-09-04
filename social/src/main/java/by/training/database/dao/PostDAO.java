package by.training.database.dao;

import java.util.List;

import by.training.model.PostModel;

public interface PostDAO {

    List<PostModel> getTopicPosts(long idTopic, int page);

}
