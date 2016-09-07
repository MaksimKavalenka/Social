package by.training.database.editor;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants.PostFields;
import by.training.database.dao.PostDAO;
import by.training.model.PostModel;

public class PostDatabaseEditor extends DatabaseEditor implements PostDAO {

    public PostDatabaseEditor() {
        super();
    }

    public PostDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public List<PostModel> getTopicPosts(final long idTopic, final int page) {
        return super.getElementsByCriteria(PostModel.class, PostFields.TOPIC, PostFields.DATE,
                idTopic, false, page);
    }

}
