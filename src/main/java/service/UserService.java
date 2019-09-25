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

    public void createUser(Map<String, String> data) {
        User user = new User(data.get("userId"), data.get("password"), data.get("name"), data.get("email"));
        log.debug("UserServiceData : {}, {}, {}, {}", data.get("userId"), data.get("password"), data.get("name"), data.get("email"));
        DataBase.addUser(user);
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
