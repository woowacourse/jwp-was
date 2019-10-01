package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginFailController extends AbstractController {
    private static final String LOGIN_FAILED_PAGE_LOCATION = "/user/login_failed.html";

    public static Controller getInstance() {
        return LazyHolder.loginFailController;
    }

    private static class LazyHolder {
        private static final Controller loginFailController = new LoginFailController();
    }

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        return HttpResponse.successByFilePath(request, LOGIN_FAILED_PAGE_LOCATION);
    }
}
