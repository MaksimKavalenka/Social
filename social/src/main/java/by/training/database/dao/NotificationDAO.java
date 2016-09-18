package by.training.database.dao;

import by.training.model.NotificationModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public interface NotificationDAO {

    NotificationModel createNotification(UserModel user, UserModel inviter, TopicModel topic);

    boolean isInvited(TopicModel topic, UserModel user);

}
