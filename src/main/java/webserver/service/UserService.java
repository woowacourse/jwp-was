package webserver.service;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.body.HttpBody;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public static void addUser(HttpBody httpBody) {
        try {
            User newUser = User.from(httpBody);
            DataBase.addUser(newUser);
            logger.debug("New User created! -> {}", newUser);
        } catch (Exception e) {
            logger.debug("This request is not for creating User");
        }
    }

    public static boolean isUser(HttpBody httpBody) {
        try {
            User user = DataBase.findUserById(httpBody.getValue("userId"));
            return user.getPassword().equals(httpBody.getValue("password"));
        } catch (NullPointerException e) {
            return false;
        }
    }
}
