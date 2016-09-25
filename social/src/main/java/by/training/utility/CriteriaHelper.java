package by.training.utility;

import static by.training.constants.CountConstants.*;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.constants.ModelStructureConstants.NotificationFields;
import by.training.constants.ModelStructureConstants.PostFields;
import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.model.Model;
import by.training.model.NotificationModel;
import by.training.model.PostModel;
import by.training.model.TopicModel;

public abstract class CriteriaHelper {

    public static <T extends Model> int getCountElements(final Class<T> clazz) {
        if (clazz == NotificationModel.class) {
            return NOTIFICATION_COUNT_ELEMENTS;
        } else if (clazz == PostModel.class) {
            return POST_COUNT_ELEMENTS;
        } else if (clazz == TopicModel.class) {
            return TOPIC_COUNT_ELEMENTS;
        }
        return 0;
    }

    public static <T extends Model> String getSortField(final Class<T> clazz) {
        if (clazz == NotificationModel.class) {
            return NotificationFields.DATE;
        } else if (clazz == PostModel.class) {
            return PostFields.DATE;
        } else if (clazz == TopicModel.class) {
            return TopicFields.NAME;
        }
        return ModelFields.ID;
    }

    public static <T extends Model> boolean getSortOrder(final Class<T> clazz) {
        if (clazz == NotificationModel.class) {
            return false;
        } else if (clazz == PostModel.class) {
            return false;
        } else if (clazz == TopicModel.class) {
            return true;
        }
        return true;
    }

}
