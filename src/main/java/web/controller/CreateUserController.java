package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.http.HttpStatus;

public class CreateUserController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = User.from(request.getRequestBody());
        logger.info("user : {}", user);
        DataBase.addUser(user);
        response.redirect("/index.html");
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        response.addHttpStatus(HttpStatus.NOT_ALLOWED_METHOD);
        ExceptionHandler.processException(new NoSuchMethodException("지원하지 않는 메서드입니다."), response);
    }
}
