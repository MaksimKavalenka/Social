package by.training.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import by.training.entity.PostEntity;

public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {

    List<PostEntity> findByTopic_Path(String topicPath, Pageable pageable);

    List<PostEntity> findByTopic_User_Id(long userId, Pageable pageable);

    long countByParentPost_Id(long id);

    long countByTopic_Path(String topicPath);

    long countByTopic_User_Id(long userId);

    // long getPostLevel(long id);

}
