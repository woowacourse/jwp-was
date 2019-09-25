package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginFailedController extends AbstractController {
    private static class LoginFailedControllerLazyHolder {
        private static final LoginFailedController INSTANCE = new LoginFailedController();
    }

    public static LoginFailedController getInstance() {
        return LoginFailedControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.setBody(FileIoUtils.loadFileFromClasspath("./templates" + "/user/login_failed" + ".html"));
            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute("Content-Type", "text/html;charset=utf-8");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
