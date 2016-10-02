package by.training.jpa.service.implementation;

import static by.training.constants.MessageConstants.TAKEN_PATH_ERROR;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import by.training.constants.EntityConstants.ElementsCount;
import by.training.constants.EntityConstants.Sort;
import by.training.entity.TopicEntity;
import by.training.entity.UserEntity;
import by.training.exception.ValidationException;
import by.training.jpa.repository.TopicRepository;
import by.training.jpa.service.dao.TopicServiceDAO;

public class TopicService implements TopicServiceDAO {

    @Autowired
    private TopicRepository repository;

    @Override
    public TopicEntity createTopic(final String name, final String path, final String description,
            final boolean access, final UserEntity creator) throws ValidationException {
        TopicEntity topic = new TopicEntity(name, path, description, access, creator);
        synchronized (TopicService.class) {
            if (!checkPath(path)) {
                return repository.save(topic);
            } else {
                throw new ValidationException(TAKEN_PATH_ERROR);
            }
        }
    }

    @Override
    public TopicEntity updateTopic(final long id, final String name, final String path,
            final String description, final boolean access) throws ValidationException {
        TopicEntity topic = getTopicById(id);
        topic.setName(name);
        topic.setPath(path);
        topic.setDescription(description);
        topic.setAccess(access);
        synchronized (TopicService.class) {
            if (!checkPath(path) || topic.getPath().equals(path)) {
                return repository.save(topic);
            } else {
                throw new ValidationException(TAKEN_PATH_ERROR);
            }
        }
    }

    @Override
    public TopicEntity getTopicById(final long id) {
        return repository.findOne(id);
    }

    @Override
    public TopicEntity getTopicByPath(final String path) {
        return repository.findByPath(path);
    }

    @Override
    public List<TopicEntity> getTopicsByValue(final String value, final long userId,
            final int page) {
        return repository
                .findDistinctByPathContainingAndUsersIdOrPathContainingAndUsersIdNotAndAccessTrue(
                        value, userId, value, userId,
                        new PageRequest(page - 1, ElementsCount.TOPIC, Sort.TOPIC));
    }

    @Override
    public List<TopicEntity> getUserTopics(final long userId, final int page) {
        return repository.findByUsersId(userId,
                new PageRequest(page - 1, ElementsCount.TOPIC, Sort.TOPIC));
    }

    @Override
    public long getTopicsByValuePageCount(final String value, final long userId) {
        return (long) Math.ceil(repository
                .countDistinctByPathContainingAndUsersIdOrPathContainingAndUsersIdNotAndAccessTrue(
                        value, userId, value, userId)
                / (double) ElementsCount.TOPIC);
    }

    @Override
    public long getUserTopicsCount(final long userId) {
        return repository.countByUsersId(userId);
    }

    @Override
    public long getUserTopicsPageCount(final long userId) {
        return (long) Math.ceil(repository.countByUsersId(userId) / (double) ElementsCount.TOPIC);
    }

    @Override
    public void joinTopic(final String path, final UserEntity user) {
        List<UserEntity> users = repository.getTopicUsers(path);
        if (!users.contains(user)) {
            TopicEntity topic = getTopicByPath(path);
            users.add(user);
            topic.setUsers(new HashSet<>(users));
            repository.save(topic);
        }
    }

    @Override
    public void leaveTopic(final String path, final UserEntity user) {
        List<UserEntity> users = repository.getTopicUsers(path);
        if (users.contains(user)) {
            TopicEntity topic = getTopicByPath(path);
            users.remove(user);
            topic.setUsers(new HashSet<>(users));
            repository.save(topic);
        }
    }

    @Override
    public boolean isPublic(final String path) {
        return repository.isPublic(path);
    }

    @Override
    public boolean isMember(final String path, final long userId) {
        return repository.isMember(path, userId);
    }

    @Override
    public boolean checkPath(final String path) {
        return repository.checkPath(path);
    }

}
