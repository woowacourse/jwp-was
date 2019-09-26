package web.controller;

import domain.db.DataBase;
import domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Responsive;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserController() {
    }

    private static class UserControllerHolder {
        private static final UserController INSTANCE = new UserController();
    }

    public static UserController getInstance() {
        return UserControllerHolder.INSTANCE;
    }

    public Responsive goForm() {
        return (request, response) -> {
            response.setContentType("text/html");
            response.forward("/user/form.html");
        };
    }

    public Responsive createUser() {
        return (request, response) -> {
            User user = new User(
                    request.getBody("userId"),
                    request.getBody("password"),
                    request.getBody("name"),
                    request.getBody("email")
            );
            logger.debug("user : {}", user);
            DataBase.addUser(user);

            response.sendRedirect("/");
        };
    }

    public Responsive goLoginForm() {
        return (request, response) -> {
            response.setContentType("text/html");
            response.forward("/user/login.html");
        };
    }

    public Responsive goLoginFail() {
        return (request, response) -> {
            response.setContentType("text/html");
            response.forward("/user/login_failed.html");
        };
    }

    public Responsive login() {
        return (request, response) -> {

            response.sendRedirect("/");
        };
    }
}
