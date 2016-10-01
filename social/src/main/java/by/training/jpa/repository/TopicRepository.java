package by.training.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.training.entity.TopicEntity;

public interface TopicRepository extends CrudRepository<TopicEntity, Long> {

    TopicEntity findByPath(String path);

    List<TopicEntity> findDistinctByPathContainingAndUsersIdOrPathContainingAndUsersIdNotAndAccessTrue(
            String value1, long userId1, String value2, long userId2, Pageable pageable);

    List<TopicEntity> findByUsersId(long userId, Pageable pageable);

    long countDistinctByPathContainingAndUsersIdOrPathContainingAndUsersIdNotAndAccessTrue(
            String value1, long userId1, String value2, long userId2);

    long countByUsersId(long userId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TopicEntity WHERE path = ?1")
    boolean checkPath(String path);

}
