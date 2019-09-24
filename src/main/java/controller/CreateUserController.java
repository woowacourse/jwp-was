package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.QueryParameter;
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
        String requestBody = new String(httpRequest.getHttpRequestBody());
        QueryParameter parameters = QueryParameter.of(requestBody);
        //TODO: 프레임워크 로직으로 빼야함
        save(parameters.getValue("userId"), parameters.getValue("password"),
                parameters.getValue("name"), parameters.getValue("email"));
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
