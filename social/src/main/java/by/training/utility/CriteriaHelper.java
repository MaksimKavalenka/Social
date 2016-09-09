package by.training.utility;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.constants.ModelStructureConstants.Models;
import by.training.constants.ModelStructureConstants.NotificationFields;
import by.training.constants.ModelStructureConstants.PostFields;
import by.training.constants.ModelStructureConstants.RelationFields;
import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.model.Model;
import by.training.model.NotificationModel;
import by.training.model.PostModel;
import by.training.model.TopicModel;

public abstract class CriteriaHelper {

    public static <T extends Model> String getSearchField(final Class<T> clazz,
            final String relation) {
        switch (relation) {
            case Models.NOTIFICATION:
                return RelationFields.NOTIFICATIONS;
            case Models.POST:
                return RelationFields.POSTS;
            case Models.TOPIC:
                if (clazz == NotificationModel.class) {
                    return PostFields.TOPIC;
                } else if (clazz == PostModel.class) {
                    return PostFields.TOPIC;
                }
                return RelationFields.TOPICS;
            case Models.USER:
                if (clazz == NotificationModel.class) {
                    return NotificationFields.USER;
                } else if (clazz == PostModel.class) {
                    return PostFields.CREATOR;
                } else if (clazz == TopicModel.class) {
                    return TopicFields.CREATOR;
                }
                return RelationFields.USERS;
            default:
                return "";
        }
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
