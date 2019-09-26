package model;

import db.DataBase;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public String addUser(HttpRequest httpRequest) {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));

        DataBase.addUser(user);
        logger.debug("insert user : {}", user);
        logger.debug("user list : {}", DataBase.findAll());

        return "/index.html";
    }
}
