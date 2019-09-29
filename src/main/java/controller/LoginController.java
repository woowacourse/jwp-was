package controller;

import controller.core.AbstractController;
import service.UserService;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseStatus;
import webserver.http.session.HttpSession;
import webserver.http.session.SessionManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class LoginController extends AbstractController {
    private static final String LOGIN_FAILED_SET_COOKIE = "logined=false; path=/user/login_failed.html";
    private static final String LOGIN_SUCCESS_SET_COOKIE = "JSESSIONID=%s; path=/";
    private final UserService userService;

    public LoginController() {
        userService = UserService.getInstance();
    }

    @Override
    public void service(OutputStream out, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        doPost(httpRequest, httpResponse);
        httpResponse.sendResponse(out, httpRequest);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!userService.loginUser(httpRequest)) {
            httpResponse.addStatus(ResponseStatus.of(302))
                    .addHeader(HttpHeaderField.LOCATION, LOGIN_FAILED)
                    .addHeader(HttpHeaderField.SET_COOKIE, LOGIN_FAILED_SET_COOKIE);
            return;
        }
        HttpSession session = SessionManager.getSession();
        session.setAttribute("user", userService.getUser(httpRequest));

        httpResponse.addStatus(ResponseStatus.of(302))
                .addHeader(HttpHeaderField.LOCATION, DEFAULT_PAGE)
                .addHeader(HttpHeaderField.SET_COOKIE, String.format(LOGIN_SUCCESS_SET_COOKIE, session.getId()));
    }
}
