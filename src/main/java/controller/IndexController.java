package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class IndexController extends AbstractController {
    private static final String TEXT_HTML = "text/html";

    private static final String INDEX_PAGE_LOCATION = "/index.html";

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        return HttpResponse.successByFilePath(request, TEXT_HTML, INDEX_PAGE_LOCATION);
    }
}