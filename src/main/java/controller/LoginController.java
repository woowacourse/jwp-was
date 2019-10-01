package controller;

import controller.core.AbstractController;
import service.UserService;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseStatus;
import webserver.http.session.HttpSession;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController extends AbstractController {
    private static final String DEFAULT_PATH = "/index.html";
    private static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
    private final UserService userService;

    LoginController() {
        userService = UserService.getInstance();
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        doPost(httpRequest, httpResponse);
        httpResponse.sendResponse(httpRequest);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!userService.loginUser(httpRequest)) {
            httpResponse.addStatus(ResponseStatus.FOUND)
                    .addHeader(HttpHeaderField.LOCATION, LOGIN_FAILED_PATH);
            return;
        }
        HttpSession session = httpRequest.getHttpSession();
        session.setAttribute(SESSION_USER_KEY, userService.getUser(httpRequest));

        httpResponse.addStatus(ResponseStatus.FOUND)
                .addHeader(HttpHeaderField.LOCATION, DEFAULT_PATH);
    }
}
