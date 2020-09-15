package webserver.controller;

import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

public class StaticController extends AbstractController {

    private StaticController() {
    }

    public static StaticController getInstance() {
        return ControllerCache.staticController;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    private static class ControllerCache {
        private static final StaticController staticController = new StaticController();
    }
}
