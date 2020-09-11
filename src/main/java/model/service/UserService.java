package model.service;

import model.db.DataBase;
import model.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public static void saveUser(Map<String, String> params) {
        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        DataBase.addUser(user);
        logger.debug("save userId : {}", user.getUserId());
    }
}
