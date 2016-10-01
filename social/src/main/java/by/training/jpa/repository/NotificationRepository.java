package by.training.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.training.entity.NotificationEntity;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUserId(long id, Pageable pageable);

    long countByUserId(long userId);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM NotificationEntity WHERE topic.path = ?1 AND user.id = ?2")
    boolean isInvited(String topicPath, long userId);

}
