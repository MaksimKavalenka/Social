package by.training.jpa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO topic_user(topic_id, user_id) VALUES ((SELECT id FROM topic WHERE path = ?1), ?2)", nativeQuery = true)
    void joinTpoic(String topicPath, long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM topic_user WHERE topic_id = (SELECT id FROM topic WHERE path = ?1) AND user_id = ?2", nativeQuery = true)
    void leaveTpoic(String topicPath, long userId);

    long countDistinctByPathContainingAndUsersIdOrPathContainingAndUsersIdNotAndAccessTrue(
            String value1, long userId1, String value2, long userId2);

    long countByUsersId(long userId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TopicEntity WHERE path = ?1 AND access IS TRUE")
    boolean isPublic(String path);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TopicEntity topic JOIN topic.users topicUsers WHERE topic.path = ?1 AND topicUsers.id = ?2")
    boolean isMember(String topicPath, long userId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TopicEntity WHERE path = ?1")
    boolean checkPath(String path);

}
