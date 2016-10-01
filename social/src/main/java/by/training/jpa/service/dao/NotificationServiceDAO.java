package by.training.jpa.service.dao;

import java.util.List;

import by.training.entity.NotificationEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;

public interface NotificationServiceDAO {

    NotificationEntity createNotification(final UserEntity user, final UserEntity inviter,
            final TopicEntity topic);

    void deleteNotification(final long id);

    List<NotificationEntity> getUserNotifications(final long userId, final int page);

    long getUserNotificationsPageCount(final long userId);

    boolean isInvited(final String topicPath, final long userId);

}
