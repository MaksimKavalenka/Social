package by.training.database.editor;

import static by.training.utility.CriteriaHelper.getCountElements;
import static by.training.utility.CriteriaHelper.getSearchField;
import static by.training.utility.CriteriaHelper.getSortField;
import static by.training.utility.CriteriaHelper.getSortOrder;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.constants.ModelStructureConstants.Models;
import by.training.constants.ModelStructureConstants.PostFields;
import by.training.constants.ModelStructureConstants.TopicFields;
import by.training.database.dao.PostDAO;
import by.training.model.PostModel;
import by.training.model.TopicModel;
import by.training.model.UserModel;

public class PostDatabaseEditor extends DatabaseEditor implements PostDAO {

    private static final Class<PostModel> clazz = PostModel.class;

    public PostDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public PostModel createPost(final String text, final UserModel creator, final TopicModel topic,
            final PostModel parentPost) {
        PostModel post = new PostModel(text, creator, topic, parentPost);
        getSessionFactory().getCurrentSession().save(post);
        return post;
    }

    @Override
    @Transactional
    public PostModel updatePost(final long id, final String text) {
        PostModel post = getPostById(id);
        post.setText(text);
        getSessionFactory().getCurrentSession().update(post);
        return post;
    }

    @Override
    @Transactional
    public void deletePost(final long id) {
        PostModel post = getPostById(id);
        getSessionFactory().getCurrentSession().delete(post);
    }

    @Override
    @Transactional
    public PostModel getPostById(final long id) {
        return (PostModel) getSessionFactory().getCurrentSession().get(clazz, id);
    }

    @Override
    @Transactional
    public List<PostModel> getTopicPosts(final String topicPath, final int page) {
        Criteria criteria = getTopicPostsCriteria(topicPath);
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public List<PostModel> getFeedPosts(final long userId, final int page) {
        Criteria criteria = getFeedPostsCriteria(userId);
        return (criteria != null)
                ? getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page)
                : null;
    }

    @Override
    @Transactional
    public long getTopicPostsPageCount(final String topicPath) {
        Criteria criteria = getTopicPostsCriteria(topicPath);
        return (long) Math.ceil((long) criteria.setProjection(Projections.rowCount()).uniqueResult()
                / (double) getCountElements(clazz));
    }

    @Override
    @Transactional
    public long getFeedPostsPageCount(final long userId) {
        Criteria criteria = getFeedPostsCriteria(userId);
        return (criteria != null) ? (long) Math
                .ceil((long) criteria.setProjection(Projections.rowCount()).uniqueResult()
                        / (double) getCountElements(clazz))
                : 0;
    }

    private Criteria getTopicPostsCriteria(final String topicPath) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.createAlias(PostFields.TOPIC, "alias");
        criteria.add(Restrictions.eq("alias." + TopicFields.PATH, topicPath));
        criteria.add(Restrictions.isNull(PostFields.PARENT_POST));
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property(PostFields.ID), PostFields.ID);
        projList.add(Projections.property(PostFields.TEXT), PostFields.TEXT);
        projList.add(Projections.property(PostFields.DATE), PostFields.DATE);
        projList.add(Projections.property(PostFields.CREATOR), PostFields.CREATOR);
        projList.add(Projections.property(PostFields.TOPIC), PostFields.TOPIC);
        criteria.setProjection(projList);
        criteria.setResultTransformer(Transformers.aliasToBean(clazz));
        return criteria;
    }

    private Criteria getFeedPostsCriteria(final long userId) {
        Class<TopicModel> topicClass = TopicModel.class;

        Criteria postCriteria = getSessionFactory().getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria topicCriteria = getSessionFactory().getCurrentSession().createCriteria(topicClass)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        topicCriteria.createAlias(getSearchField(topicClass, Models.USER), "alias");
        topicCriteria.add(Restrictions.eq("alias." + ModelFields.ID, userId));

        if (topicCriteria.list().isEmpty()) {
            return null;
        }

        postCriteria
                .add(Restrictions.in(getSearchField(clazz, Models.TOPIC), topicCriteria.list()));
        postCriteria.add(Restrictions.isNull(PostFields.PARENT_POST));
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property(PostFields.ID), PostFields.ID);
        projList.add(Projections.property(PostFields.TEXT), PostFields.TEXT);
        projList.add(Projections.property(PostFields.DATE), PostFields.DATE);
        projList.add(Projections.property(PostFields.CREATOR), PostFields.CREATOR);
        projList.add(Projections.property(PostFields.TOPIC), PostFields.TOPIC);
        postCriteria.setProjection(projList);
        postCriteria.setResultTransformer(Transformers.aliasToBean(clazz));
        return postCriteria;
    }

}
