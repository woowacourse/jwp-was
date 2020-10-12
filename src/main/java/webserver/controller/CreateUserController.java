package webserver.controller;

import db.DataBase;
import exception.RequestParameterNotFoundException;
import model.User;
import web.request.HttpRequest;
import web.response.HttpResponse;

public class CreateUserController extends AbstractController {

    public static final String CREATE_USER_LOGGING_MESSAGE = "New User created! -> {}";
    public static final String INDEX_HTML_PATH = "/index.html";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            User user = User.builder()
                    .userId(request.getRequestBodyByKey("userId"))
                    .email(request.getRequestBodyByKey("email"))
                    .password(request.getRequestBodyByKey("password"))
                    .name(request.getRequestBodyByKey("name"))
                    .build();
            DataBase.addUser(user);
            logger.debug(CREATE_USER_LOGGING_MESSAGE, user);
            response.found(INDEX_HTML_PATH);
        } catch (RequestParameterNotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
