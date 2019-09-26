package model;

import db.DataBase;
import model.exception.LoginFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public String login(Map<String, String> map) {
        String userId = map.get("userId");
        String password = map.get("password");

        User user = Optional.ofNullable(DataBase.findUserById(userId))
                .orElseThrow(() -> new LoginFailException("Not Exist User ID!"));

        if (!user.isSamePassword(password)) {
            throw new LoginFailException("Not Match User Password!");
        }
        logger.debug("login success : {}", user);

        return "/index.html";
    }
}
