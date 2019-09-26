package model;

import db.DataBase;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public String addUser(HttpRequest httpRequest) {
        User user = createUser(httpRequest);

        DataBase.addUser(user);
        logger.debug("insert user : {}", user);
        logger.debug("user list : {}", DataBase.findAll());

        return "/index.html";
    }

    // @TODO 리팩토링
    public String login(HttpRequest httpRequest) {
        User loginUser = createUser(httpRequest);
        User other;

        try {
            other = Optional.ofNullable(DataBase.findUserById(loginUser.getUserId()))
                    .orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException e) {
            return "/user/login_failed.html";
        }

        if (loginUser.match(other)) {
            return "redirect:/index.html";
        }
        return "/user/login_failed.html";
    }

    private User createUser(HttpRequest httpRequest) {
        return new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
    }
}
