package by.training.database.dao;

import java.util.List;

import by.training.model.NotificationModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public interface NotificationDAO {

    NotificationModel createNotification(UserModel user, UserModel inviter, TopicModel topic);

    void deleteNotification(long id);

    NotificationModel getNotificationById(long id);

    List<NotificationModel> getUserNotifications(long userId, int page);

    long getUserNotificationsPageCount(long userId);

    boolean isInvited(TopicModel topic, UserModel user);

}
