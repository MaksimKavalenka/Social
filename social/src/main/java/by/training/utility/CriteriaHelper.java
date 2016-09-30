package by.training.utility;

import static by.training.constants.CountConstants.*;

import by.training.constants.EntityConstants.Structure;
import by.training.entity.AbstractEntity;
import by.training.entity.NotificationEntity;
import by.training.entity.PostEntity;
import by.training.entity.TopicEntity;

public abstract class CriteriaHelper {

    public static <T extends AbstractEntity> int getCountElements(final Class<T> clazz) {
        if (clazz == NotificationEntity.class) {
            return NOTIFICATION_COUNT_ELEMENTS;
        } else if (clazz == PostEntity.class) {
            return POST_COUNT_ELEMENTS;
        } else if (clazz == TopicEntity.class) {
            return TOPIC_COUNT_ELEMENTS;
        }
        return 0;
    }

    public static <T extends AbstractEntity> String getSortField(final Class<T> clazz) {
        if (clazz == NotificationEntity.class) {
            return Structure.NotificationFields.DATE;
        } else if (clazz == PostEntity.class) {
            return Structure.PostFields.DATE;
        } else if (clazz == TopicEntity.class) {
            return Structure.TopicFields.NAME;
        }
        return Structure.AbstractFields.ID;
    }

    public static <T extends AbstractEntity> boolean getSortOrder(final Class<T> clazz) {
        if (clazz == NotificationEntity.class) {
            return false;
        } else if (clazz == PostEntity.class) {
            return false;
        } else if (clazz == TopicEntity.class) {
            return true;
        }
        return true;
    }

}
