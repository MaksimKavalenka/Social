package by.training.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import by.training.entity.PostEntity;

public interface PostRepository extends CrudRepository<PostEntity, Long> {

    List<PostEntity> findByTopicPath(String topicPath, Pageable pageable);

    List<PostEntity> findByTopicUsersId(long userId, Pageable pageable);

    long countByParentPost_Id(long id);

    long countByTopicPath(String topicPath);

    long countByTopicUsersId(long userId);

    // long getPostLevel(long id);

}
