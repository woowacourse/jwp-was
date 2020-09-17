package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.Controller;
import annotation.RequestMapping;
import db.DataBase;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public static String create(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}, request : {}", request, response);

        User user = new User(
            request.getHttpBodyValueOf("userId"),
            request.getHttpBodyValueOf("password"),
            request.getHttpBodyValueOf("name"),
            request.getHttpBodyValueOf("email")
        );
        DataBase.addUser(user);
        logger.debug("Saved User: {}", DataBase.findUserById(request.getHttpBodyValueOf("userId")));

        return "/index.html";
    }
}
