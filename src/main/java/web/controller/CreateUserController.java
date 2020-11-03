package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import web.http.HttpRequest;
import web.http.HttpResponse;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = User.from(request.getRequestBody());
        logger.info("user : {}", user);
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
