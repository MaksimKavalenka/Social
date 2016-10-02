package by.training.jpa.service.implementation;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import by.training.constants.EntityConstants.ElementsCount;
import by.training.constants.EntityConstants.Sort;
import by.training.entity.PostEntity;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.jpa.repository.PostRepository;
import by.training.jpa.service.dao.PostServiceDAO;

public class PostService implements PostServiceDAO {

    @Autowired
    private PostRepository repository;

    @Override
    public PostEntity createPost(final String text, final UserEntity creator,
            final TopicEntity topic, final PostEntity parentPost) {
        PostEntity post = new PostEntity(text, creator, topic, parentPost);
        return repository.save(post);
    }

    @Override
    public PostEntity updatePost(final long id, final String text) {
        PostEntity post = getPostById(id);
        post.setText(text);
        return repository.save(post);
    }

    @Override
    public void deletePost(final long id) {
        repository.delete(id);
    }

    @Override
    public PostEntity getPostById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public List<PostEntity> getPostComments(final long id) {
        List<PostEntity> posts = repository.getPostComments(id);
        for (PostEntity post : posts) {
            post.setPosts(new HashSet<>(getPostComments(post.getId())));
        }
        return posts;
    }

    @Override
    public List<PostEntity> getTopicPosts(final String topicPath, final int page) {
        return repository.findByTopicPath(topicPath,
                new PageRequest(page - 1, ElementsCount.POST, Sort.POST));
    }

    @Override
    public List<PostEntity> getFeedPosts(final long userId, final int page) {
        return repository.getFeedPosts(userId,
                new PageRequest(page - 1, ElementsCount.POST, Sort.POST));
    }

    @Override
    public long getPostCommentsCount(final long id) {
        return repository.countByParentPost_Id(id);
    }

    @Override
    public long getTopicPostsPageCount(final String topicPath) {
        return (long) Math
                .ceil(repository.countByTopicPath(topicPath) / (double) ElementsCount.POST);
    }

    @Override
    public long getFeedPostsPageCount(final long userId) {
        return (long) Math.ceil(repository.getFeedPostsCount(userId) / (double) ElementsCount.POST);
    }

    @Override
    public long getPostLevel(final long id) {
        Long currentId = repository.getParentPostId(id);
        long level = 0;
        while (currentId != null) {
            ++level;
            currentId = repository.getParentPostId(currentId);
        }
        return level;
    }

}
