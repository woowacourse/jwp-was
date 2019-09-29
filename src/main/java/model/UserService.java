package model;

import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserService() {
    }

    public static UserService getInstance() {
        return UserServiceLazyHolder.INSTANCE;
    }

    private static class UserServiceLazyHolder {
        private static final UserService INSTANCE = new UserService();
    }

    public void addUser(Map<String, String> map) {
        User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
        DataBase.addUser(user);
        logger.debug("insert user : {}", user);
        logger.debug("user list : {}", DataBase.findAll());
    }

    public List<User> findAll() {
        return new ArrayList<>(DataBase.findAll());
    }
}
