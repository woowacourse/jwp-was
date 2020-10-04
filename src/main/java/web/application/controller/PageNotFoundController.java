package web.application.controller;

import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

public class PageNotFoundController extends AbstractController {

    private PageNotFoundController() {
        super();
    }

    public static PageNotFoundController getInstance() {
        return PageNotFoundControllerCache.pageNotFoundController;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.respondPageNotFound();
    }

    private static class PageNotFoundControllerCache {
        private static final PageNotFoundController pageNotFoundController = new PageNotFoundController();
    }
}
