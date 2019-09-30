package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.headerfields.MimeType;

public class LoginFailController extends AbstractController {
    private static final String LOGIN_FAILED_PAGE_LOCATION = "/user/login_failed.html";

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        return HttpResponse.successByFilePath(request, MimeType.TEXT_HTML, LOGIN_FAILED_PAGE_LOCATION);
    }
}
