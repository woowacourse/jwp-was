package controller;

import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpContentType;

public class IndexController {
    public static HttpResponse index(HttpRequest req) {
        return FileIoUtils.loadFileFromClasspath("static/index.html").map(body ->
            HttpResponse.builder(HttpContentType.TEXT_HTML_UTF_8)
                        .extractFromRequest(req)
                        .body(body)
                        .build()
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }
}