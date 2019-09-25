package dev.luffy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.luffy.annotation.Controller;
import dev.luffy.annotation.RequestMapping;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;
import dev.luffy.model.User;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/user/form.html")
    public static void formUser(HttpRequest request, HttpResponse response) {

        logger.debug("request : {} & response : {}", request, response);

        response.ok(request);
    }

    @RequestMapping("/user/create")
    public static void createUser(HttpRequest request, HttpResponse response) {

        logger.debug("request : {} & response : {}", request, response);

        User user = new User(
                request.getBodyParameter("userId"),
                request.getBodyParameter("password"),
                request.getBodyParameter("name"),
                request.getBodyParameter("email")
        );

        logger.debug("Generated User : {}", user);

        response.redirect(request, "/index.html");
    }
}
