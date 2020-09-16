package web.application.controller;

import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

public class RootController extends AbstractController {

    private RootController() {
        super();
    }

    public static RootController getInstance() {
        return ControllerCache.rootController;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward("/index.html");
    }

    private static class ControllerCache {
        private static final RootController rootController = new RootController();
    }
}
