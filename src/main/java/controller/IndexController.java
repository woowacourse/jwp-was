package controller;

import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class IndexController {
    private static final String TEXT_HTML = "text/html";

    public static HttpResponse index(HttpRequest request) {
        return FileIoUtils.loadFileFromClasspath("./templates/index.html").map(body ->
                HttpResponse.success(request, TEXT_HTML, body)
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }
}