package dev.luffy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.luffy.annotation.Controller;
import dev.luffy.annotation.RequestMapping;
import dev.luffy.db.DataBase;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;
import dev.luffy.model.User;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/user/form.html")
    public static void form(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        if (request.isGet()) {
            response.ok(request);
            return;
        }

        response.notFound(request);
    }

    @RequestMapping("/user/login.html")
    public static void login(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        if (request.isGet()) {
            response.ok(request);
            return;
        }

        response.notFound(request);
    }

    @RequestMapping("/user/login_failed.html")
    public static void loginFailed(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        if (request.isGet()) {
            response.ok(request);
            return;
        }

        response.notFound(request);
    }

    @RequestMapping("/user/list.html")
    public static void list(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        if (!request.isLoggedIn()) {
            response.redirect(request, "/index.html");
            return;
        }

        if (request.isGet()) {
            response.ok(request);
            return;
        }

        response.notFound(request);
    }

    @RequestMapping("/user/create")
    public static void createUser(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        if (request.isPost()) {
            User user = new User(
                    request.getBodyParameter("userId"),
                    request.getBodyParameter("password"),
                    request.getBodyParameter("name"),
                    request.getBodyParameter("email")
            );

            logger.debug("Generated User : {}", user);

            DataBase.addUser(user);

            response.redirect(request, "/index.html");
            return;
        }

        response.notFound(request);
    }

    @RequestMapping("/user/login")
    public static void loginUser(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        if (request.isPost()) {
            String userId = request.getBodyParameter("userId");
            User loginUser = DataBase.findUserById(userId);

            logger.debug("login User : {}", loginUser);

            if (loginUser.matchPassword(request.getBodyParameter("password"))) {
                response.addHeader("Set-Cookie", "logined=true; Path=/");
            } else {
                response.addHeader("Set-Cookie", "logined=false; Path=/");
            }

            response.redirect(request, "/index.html");
            return;
        }

        response.notFound(request);
    }

    @RequestMapping("/user/list")
    public static void userList(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}", request);

        if (request.isGet()) {
            if (request.isLoggedIn()) {
                response.redirect(request, "/user/list.html");
            } else {
                response.redirect(request, "/user/login.html");
            }

            return;
        }

        response.notFound(request);
    }
}
