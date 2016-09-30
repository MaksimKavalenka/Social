package by.training.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import by.training.entity.NotificationEntity;

public interface NotificationRepository
        extends PagingAndSortingRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUser_Id(long id, Pageable pageable);

    long countByUser_Id(long userId);

    boolean existsByTopic_PathAndUser_Id(String topicPath, long userId);

}
