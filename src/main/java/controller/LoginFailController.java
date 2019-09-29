package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginFailController extends AbstractController {
    private static final String TEXT_HTML = "text/html";

    private static final String LOGIN_FAILED_PAGE_LOCATION = "/user/login_failed.html";

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        return HttpResponse.successByFilePath(request, TEXT_HTML, LOGIN_FAILED_PAGE_LOCATION);
    }
}
