package by.training.database.dao;

import java.util.List;

import by.training.model.Model;
import by.training.model.PostModel;

public interface RelationDAO {

    <T extends Model> List<T> getElementsByCriteria(Class<T> clazz, String relation, long id,
            int page);

    List<PostModel> getPosts(long id, int page);

}
