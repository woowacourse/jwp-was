package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        String requestBody = new String(httpRequest.getHttpRequestBody());
        String[] tokens = requestBody.split("&");
        Map<String, String> parameters = new HashMap<>();

        Arrays.stream(tokens)
                .forEach(s -> parameters.put(s.split("=")[0], s.split("=")[1]));

        save(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        httpResponse.sendRedirect("index.html");
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
