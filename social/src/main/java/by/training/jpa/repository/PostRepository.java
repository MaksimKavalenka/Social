package by.training.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.training.entity.PostEntity;

public interface PostRepository extends CrudRepository<PostEntity, Long> {

    List<PostEntity> findByTopicPath(String topicPath, Pageable pageable);

    @Query("SELECT post FROM PostEntity post JOIN post.topic.users postTopicUsers WHERE postTopicUsers.id = ?1 AND post.parentPost IS NULL")
    List<PostEntity> getFeedPosts(long userId, Pageable pageable);

    @Query("SELECT post.posts FROM PostEntity post WHERE post.id = ?1")
    List<PostEntity> getPostComments(long id);

    long countByParentPost_Id(long id);

    long countByTopicPath(String topicPath);

    @Query("SELECT COUNT(*) FROM PostEntity post JOIN post.topic.users postTopicUsers WHERE postTopicUsers.id = ?1 AND post.parentPost IS NULL")
    long getFeedPostsCount(long userId);

    @Query("SELECT post.parentPost.id FROM PostEntity post WHERE post.id = ?1")
    Long getParentPostId(long id);

}
