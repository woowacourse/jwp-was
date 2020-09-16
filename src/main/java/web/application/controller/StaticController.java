package web.application.controller;

import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;

public class StaticController extends AbstractController {

    private StaticController() {
        super();
    }

    public static StaticController getInstance() {
        return ControllerCache.staticController;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }

    private static class ControllerCache {
        private static final StaticController staticController = new StaticController();
    }
}
