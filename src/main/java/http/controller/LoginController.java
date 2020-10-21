package http.controller;

import java.util.Objects;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.response.HttpResponse;
import model.User;

public class LoginController extends HttpRequestMappingAbstractController {

    public LoginController(HttpRequestMapping httpRequestMapping) {
        super(httpRequestMapping);
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getRequestBody().parseBody().get("userId");
        String password = httpRequest.getRequestBody().parseBody().get("password");

        User user = DataBase.findUserById(userId);

        if (authenticateUser(user, password)) {
            httpResponse.setCookie("logined=true; Path=/");
            httpResponse.redirect("/index.html");
            return;
        }

        httpResponse.setCookie("logined=false");
        httpResponse.redirect("/user/login_failed.html");
    }

    private boolean authenticateUser(User user, String password) {
        return Objects.nonNull(user) && user.validatePassword(password);
    }
}
