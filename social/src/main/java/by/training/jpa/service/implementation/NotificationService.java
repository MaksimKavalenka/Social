package by.training.jpa.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import by.training.constants.EntityConstants.ElementsCount;
import by.training.constants.EntityConstants.Sort;
import by.training.entity.NotificationEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.jpa.repository.NotificationRepository;
import by.training.jpa.service.dao.NotificationServiceDAO;

public class NotificationService implements NotificationServiceDAO {

    @Autowired
    private NotificationRepository repository;

    @Override
    public NotificationEntity createNotification(final UserEntity user, final UserEntity inviter,
            final TopicEntity topic) {
        NotificationEntity notification = new NotificationEntity(user, inviter, topic);
        return repository.save(notification);
    }

    @Override
    public void deleteNotification(final long id) {
        repository.delete(id);
    }

    @Override
    public List<NotificationEntity> getUserNotifications(final long userId, final int page) {
        return repository.findByUserId(userId,
                new PageRequest(page - 1, ElementsCount.NOTIFICATION, Sort.NOTIFICATION));
    }

    @Override
    public long getUserNotificationsPageCount(final long userId) {
        return (long) Math
                .ceil(repository.countByUserId(userId) / (double) ElementsCount.NOTIFICATION);
    }

    @Override
    public boolean isInvited(final String topicPath, final long userId) {
        return repository.isInvited(topicPath, userId);
    }

}
