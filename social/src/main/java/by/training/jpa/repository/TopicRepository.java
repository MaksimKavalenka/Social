package by.training.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;

public interface TopicRepository extends CrudRepository<TopicEntity, Long> {

    TopicEntity findByPath(String path);

    List<TopicEntity> findDistinctByPathContainingAndUsersIdOrPathContainingAndUsersIdNotAndAccessTrue(
            String value1, long userId1, String value2, long userId2, Pageable pageable);

    List<TopicEntity> findByUsersId(long userId, Pageable pageable);

    @Query("SELECT topic.users FROM TopicEntity topic WHERE topic.path = ?1")
    List<UserEntity> getTopicUsers(String path);

    long countDistinctByPathContainingAndUsersIdOrPathContainingAndUsersIdNotAndAccessTrue(
            String value1, long userId1, String value2, long userId2);

    long countByUsersId(long userId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TopicEntity WHERE path = ?1 AND access IS TRUE")
    boolean isPublic(String path);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TopicEntity topic JOIN topic.users topicUsers WHERE topic.path = ?1 AND topicUsers.id = ?2")
    boolean isMember(String path, long userId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TopicEntity WHERE path = ?1")
    boolean checkPath(String path);

}
