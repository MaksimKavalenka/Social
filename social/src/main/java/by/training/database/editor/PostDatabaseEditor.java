package by.training.database.editor;

import static by.training.utility.CriteriaHelper.getCountElements;
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

import by.training.constants.EntityConstants.Structure;
import by.training.database.dao.PostDAO;
import by.training.entity.PostEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;

public class PostDatabaseEditor extends DatabaseEditor implements PostDAO {

    private static final Class<PostEntity> clazz = PostEntity.class;

    public PostDatabaseEditor(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional
    public PostEntity createPost(final String text, final UserEntity creator,
            final TopicEntity topic, final PostEntity parentPost) {
        PostEntity post = new PostEntity(text, creator, topic, parentPost);
        getSessionFactory().getCurrentSession().save(post);
        return post;
    }

    @Override
    @Transactional
    public PostEntity updatePost(final long id, final String text) {
        PostEntity post = getPostById(id);
        post.setText(text);
        getSessionFactory().getCurrentSession().update(post);
        return post;
    }

    @Override
    @Transactional
    public void deletePost(final long id) {
        PostEntity post = getPostById(id);
        getSessionFactory().getCurrentSession().delete(post);
    }

    @Override
    @Transactional
    public PostEntity getPostById(final long id) {
        return getSessionFactory().getCurrentSession().get(clazz, id);
    }

    @Override
    @Transactional
    public List<PostEntity> getTopicPosts(final String topicPath, final int page) {
        Criteria criteria = getTopicPostsCriteria(topicPath);
        return getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page);
    }

    @Override
    @Transactional
    public List<PostEntity> getFeedPosts(final long userId, final int page) {
        Criteria criteria = getFeedPostsCriteria(userId);
        return (criteria != null)
                ? getElements(criteria, clazz, getSortField(clazz), getSortOrder(clazz), page)
                : null;
    }

    /* Only first-level comments */
    @Override
    @Transactional
    public long getPostCommentsCount(final long id) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz);

        criteria.createAlias(Structure.PostFields.PARENT_POST, "alias");
        criteria.add(Restrictions.eq("alias." + Structure.TopicFields.ID, id));

        return (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
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

    @Override
    @Transactional
    public long getPostLevel(final long id) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz);
        criteria.add(Restrictions.idEq(id));

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property(Structure.PostFields.PARENT_POST),
                Structure.PostFields.PARENT_POST);

        criteria.setProjection(projList);
        criteria.setResultTransformer(Transformers.aliasToBean(clazz));

        PostEntity post = (PostEntity) criteria.uniqueResult();
        long level = 0;

        while (post.getParentPost() != null) {
            ++level;
            post = post.getParentPost();
        }
        return level;
    }

    private Criteria getTopicPostsCriteria(final String topicPath) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        criteria.createAlias(Structure.PostFields.TOPIC, "alias");
        criteria.add(Restrictions.eq("alias." + Structure.TopicFields.PATH, topicPath));
        criteria.add(Restrictions.isNull(Structure.PostFields.PARENT_POST));

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property(Structure.PostFields.ID), Structure.PostFields.ID);
        projList.add(Projections.property(Structure.PostFields.TEXT), Structure.PostFields.TEXT);
        projList.add(Projections.property(Structure.PostFields.DATE), Structure.PostFields.DATE);
        projList.add(Projections.property(Structure.PostFields.CREATOR),
                Structure.PostFields.CREATOR);
        projList.add(Projections.property(Structure.PostFields.TOPIC), Structure.PostFields.TOPIC);

        criteria.setProjection(projList);
        criteria.setResultTransformer(Transformers.aliasToBean(clazz));

        return criteria;
    }

    private Criteria getFeedPostsCriteria(final long userId) {
        Class<TopicEntity> topicClass = TopicEntity.class;

        Criteria postCriteria = getSessionFactory().getCurrentSession().createCriteria(clazz)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        Criteria topicCriteria = getSessionFactory().getCurrentSession().createCriteria(topicClass)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        topicCriteria.createAlias(Structure.RelationFields.USERS, "alias");
        topicCriteria.add(Restrictions.eq("alias." + Structure.UserFields.ID, userId));

        if (topicCriteria.list().isEmpty()) {
            return null;
        }

        postCriteria.add(Restrictions.in(Structure.PostFields.TOPIC, topicCriteria.list()));
        postCriteria.add(Restrictions.isNull(Structure.PostFields.PARENT_POST));

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property(Structure.PostFields.ID), Structure.PostFields.ID);
        projList.add(Projections.property(Structure.PostFields.TEXT), Structure.PostFields.TEXT);
        projList.add(Projections.property(Structure.PostFields.DATE), Structure.PostFields.DATE);
        projList.add(Projections.property(Structure.PostFields.CREATOR),
                Structure.PostFields.CREATOR);
        projList.add(Projections.property(Structure.PostFields.TOPIC), Structure.PostFields.TOPIC);

        postCriteria.setProjection(projList);
        postCriteria.setResultTransformer(Transformers.aliasToBean(clazz));

        return postCriteria;
    }

}
