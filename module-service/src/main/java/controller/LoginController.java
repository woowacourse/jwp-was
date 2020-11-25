package controller;

import java.io.IOException;
import java.util.Objects;

import http.HttpSession;
import http.HttpSessionStorage;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import service.UserService;
import type.method.MethodType;

public class LoginController extends AbstractController {

    private static final String REDIRECT_HOME = "/index.html";
    private static final String REDIRECT_LOGIN_FAILED = "/user/login_failed.html";
    private static final String USER_ID = "userId";
    private static final String LOGINED = "logined";
    private static final String SESSION_ID = "SessionId";

    private final UserService userService = UserService.getInstance();

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.response405(MethodType.POST.name());
    }

    @Override
    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        final User user = userService.login(httpRequest.getHttpRequestBodyByName(USER_ID));
        if (Objects.isNull(user)) {
            httpResponse.addCookie(LOGINED, "false");
            httpResponse.response302(REDIRECT_LOGIN_FAILED);
            return ;
        }

        HttpSession httpSession = httpRequest.getHttpSession();
        httpSession.setAttribute(user.getUserId(), user);
        HttpSessionStorage.save(httpSession.getId(), httpSession);

        httpResponse.addCookie(SESSION_ID, httpRequest.getSessionId());
        httpResponse.addCookie(LOGINED, "true");

        httpResponse.response302(REDIRECT_HOME);
    }
}
