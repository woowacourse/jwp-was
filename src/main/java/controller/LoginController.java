package controller;

import java.io.IOException;
import java.util.Objects;

import http.HttpSession;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import service.UserService;

public class LoginController extends AbstractController {

    private static final String REDIRECT_HOME = "/index.html";
    private static final String REDIRECT_LOGIN_FAILED = "/user/login_failed.html";
    private static final String LOGINED = "logined";
    private static final String SESSION_ID = "SessionId";

    private final UserService userService = UserService.getInstance();

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        final User user = userService.login(httpRequest);
        if (Objects.isNull(user)) {
            httpResponse.addCookie(LOGINED, "false");
            httpResponse.response302(REDIRECT_LOGIN_FAILED);
            return ;
        }
        HttpSession httpSession = new HttpSession();
        httpSession.setAttribute(SESSION_ID, httpSession.getId());
        httpSessionStorage.save(httpSession.getId(), httpSession);

        httpResponse.addCookie(SESSION_ID, httpSession.getId());
        httpResponse.addCookie(LOGINED, "true");

        httpResponse.response302(REDIRECT_HOME);
    }
}
