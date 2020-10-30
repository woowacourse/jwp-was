package controller;

import java.io.IOException;
import java.util.Objects;

import http.Cookies;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import service.UserService;

public class LoginController extends AbstractController {

    private static final String REDIRECT_HOME = "http://localhost:8080/index.html";
    private static final String REDIRECT_LOGIN_FAILED = "http://localhost:8080/user/login_failed.html";
    private static final String LOGINED = "logined";

    private final UserService userService = UserService.getInstance();

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        final User user = userService.login(httpRequest);
        if (Objects.isNull(user)) {
            httpResponse.addCookie(LOGINED, false);
            httpResponse.response302(REDIRECT_LOGIN_FAILED);
            return ;
        }
        httpResponse.addCookie(LOGINED, true);
        httpResponse.response302(REDIRECT_HOME);
    }
}
