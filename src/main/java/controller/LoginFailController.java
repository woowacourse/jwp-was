package controller;

import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginFailController extends AbstractController {
    private static final String TEXT_HTML = "text/html";

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        String filePath = FileIoUtils.convertPath("/user/login_failed.html");

        return FileIoUtils.loadFileFromClasspath(filePath)
                .map(body -> HttpResponse.success(request, TEXT_HTML, body))
                .orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }
}
