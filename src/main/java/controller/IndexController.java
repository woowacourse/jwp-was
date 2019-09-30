package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.headerfields.MimeType;

public class IndexController extends AbstractController {
    private static final String INDEX_PAGE_LOCATION = "/index.html";

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        return HttpResponse.successByFilePath(request, MimeType.TEXT_HTML, INDEX_PAGE_LOCATION);
    }
}