package by.training.database.dao;

import java.util.List;

import by.training.model.Model;
import by.training.model.PostModel;
import by.training.model.TopicModel;

public interface RelationDAO {

    <T extends Model> List<T> getElementsByCriteria(Class<T> clazz, String relation, long id,
            int page);

    List<PostModel> getFeedPosts(long userId, int page);

    List<TopicModel> getTopicsByValue(String value, long userId, int page);

    <T extends Model> long getElementsByCriteriaCount(Class<T> clazz, String relation, long id);

    <T extends Model> long getElementsByCriteriaPageCount(Class<T> clazz, String relation, long id);

    long getFeedPostsPageCount(long userId);

    long getTopicsByValuePageCount(String value, long userId);

}
