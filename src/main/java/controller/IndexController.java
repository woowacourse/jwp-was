package controller;

import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class IndexController {
    private static final String TEXT_HTML = "text/html";
    private static final String INDEX_HTML = "/index.html";

    public static HttpResponse index(HttpRequest request) {
        String filePath = FileIoUtils.convertPath(INDEX_HTML);

        return FileIoUtils.loadFileFromClasspath(filePath).map(body ->
                HttpResponse.success(request, TEXT_HTML, body)
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }
}