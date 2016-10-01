package by.training.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import by.training.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);

    List<UserEntity> findAllByTopicsPathNotLikeOrderByLoginAsc(String topicPath);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserEntity WHERE login = ?1")
    boolean checkLogin(String login);

}
