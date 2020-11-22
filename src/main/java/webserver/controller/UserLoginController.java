package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.Parameters;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionStorage;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class UserLoginController extends AbstractController {
    private static final String USER_LOGIN_SUCCESS_PATH = "/index.html";
    private static final String USER_LOGIN_FAILED_PATH = "/user/login_failed.html";

    public UserLoginController() {
        this.paths = Collections.singletonList("/user/login");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Parameters parameters = httpRequest.getBodyParameters();
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");

        if (isValidUser(userId, password)) {
            HttpSession httpSession = HttpSessionStorage.create(userId);
            httpResponse.addHeader("Set-Cookie", httpSession.getHttpSessionString());
            httpResponse.sendRedirect(USER_LOGIN_SUCCESS_PATH);
        } else {
            httpResponse.sendRedirect(USER_LOGIN_FAILED_PATH);
        }
    }

    private boolean isValidUser(String userId, String password) {
        User user = DataBase.findUserById(userId);
        return Objects.nonNull(user)
                && user.isSamePassword(password);
    }
}
