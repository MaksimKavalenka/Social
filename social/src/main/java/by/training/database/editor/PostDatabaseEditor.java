package by.training.database.editor;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import by.training.database.dao.PostDAO;
import by.training.model.PostModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public class PostDatabaseEditor extends DatabaseEditor implements PostDAO {

    public PostDatabaseEditor() {
        super();
    }

    public PostDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public PostModel createPost(final String text, final UserModel creator, final TopicModel topic,
            final PostModel parentPost) {
        PostModel post = new PostModel(text, creator, topic, parentPost);
        sessionFactory.getCurrentSession().save(post);
        return post;
    }

    @Override
    @Transactional
    public PostModel getPostById(final long id) {
        return (PostModel) sessionFactory.getCurrentSession().get(PostModel.class, id);
    }

}
