package client.controller;

import client.db.DataBase;
import client.model.User;
import was.webserver.controller.AbstractController;
import was.webserver.http.Parameters;
import was.webserver.http.request.HttpRequest;
import was.webserver.http.response.HttpResponse;
import was.webserver.http.session.HttpSession;
import was.webserver.http.session.HttpSessionStorage;

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
