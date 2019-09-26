package webserver.controller;

import db.DataBase;
import factory.UserFactory;
import model.User;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.util.Map;

public class UserController extends AbstractController {
    public static final String SAVE_REDIRECT_URL = "/index.html";

    private UserController() {
    }

    public static UserController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final UserController INSTANCE = new UserController();
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> requestBodyFields = httpRequest.getBodyFields();
        User user = UserFactory.of(requestBodyFields);
        DataBase.addUser(user);
        httpResponse.sendRedirect(SAVE_REDIRECT_URL);
    }
}
