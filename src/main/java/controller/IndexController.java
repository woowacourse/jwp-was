package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class IndexController extends AbstractController {
    private static final String INDEX_PAGE_LOCATION = "/index.html";

    public static Controller getInstance() {
        return LazyHolder.indexController;
    }

    private static class LazyHolder {
        private static final Controller indexController = new IndexController();
    }

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        return HttpResponse.successByFilePath(request, INDEX_PAGE_LOCATION);
    }
}