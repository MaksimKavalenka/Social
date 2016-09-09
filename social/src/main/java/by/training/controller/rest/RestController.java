package by.training.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;

import by.training.database.dao.PostDAO;
import by.training.database.dao.RelationDAO;
import by.training.database.dao.RoleDAO;
import by.training.database.dao.TopicDAO;
import by.training.database.dao.UserDAO;

public class RestController {

    public static final String JSON_EXT = ".json";

    @Autowired
    public PostDAO             postDAO;
    @Autowired
    public RoleDAO             roleDAO;
    @Autowired
    public TopicDAO            topicDAO;
    @Autowired
    public UserDAO             userDAO;
    @Autowired
    public RelationDAO         relationDAO;

}
