package controller;

import controller.core.AbstractController;
import service.UserService;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class LoginController extends AbstractController {
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
        super.doPost(httpRequest, httpResponse);
        if (!userService.loginUser(httpRequest)) {
            httpResponse.addStatus(ResponseStatus.of(302));
            httpResponse.addHeader(HttpHeaderField.SET_COOKIE, "logined=false; path=/user/login_failed.html");
            httpResponse.addHeader(HttpHeaderField.LOCATION, LOGIN_FAILED);
            return;
        }
        httpResponse.addStatus(ResponseStatus.of(302));
        httpResponse.addHeader(HttpHeaderField.SET_COOKIE, "logined=true; path=/");
        httpResponse.addHeader(HttpHeaderField.LOCATION, DEFAULT_PAGE);
    }
}
