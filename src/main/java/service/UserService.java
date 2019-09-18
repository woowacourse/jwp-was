package service;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    public User createUser(Map<String, String> params) {
        log.debug("userService: {}, {}, {}", params.get("password"), params.get("name"), params.get("email"));
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        DataBase.addUser(user);
        return user;
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
