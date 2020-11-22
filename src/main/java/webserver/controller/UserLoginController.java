package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.Parameters;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class UserLoginController extends AbstractController {
    public UserLoginController() {
        this.paths = Collections.singletonList("/user/login");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Parameters parameters = httpRequest.getBodyParameters();
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");

        if (isValidUser(userId, password)) {
            httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
            httpResponse.sendRedirect("/index.html");
        } else {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }

    private boolean isValidUser(String userId, String password) {
        User user = DataBase.findUserById(userId);
        return Objects.nonNull(user)
                && user.isSamePassword(password);
    }
}
