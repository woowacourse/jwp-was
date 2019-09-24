package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {
    private CreateUserController() {

    }

    public static CreateUserController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final CreateUserController INSTANCE = new CreateUserController();
    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        save(httpRequest.getQueryValue("userId"), httpRequest.getQueryValue("password"),
                httpRequest.getQueryValue("name"), httpRequest.getQueryValue("email"));
        httpResponse.redirect("index.html");
    }

    private void save(String userId, String password, String name, String email) {
        if (DataBase.findUserById(userId) == null) {
            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            return;
        }

        throw new IllegalArgumentException();
    }
}
