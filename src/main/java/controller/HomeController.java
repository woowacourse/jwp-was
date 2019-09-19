package controller;

import http.request.Request;
import http.response.Response;
import http.response.ResponseFactory;

public class HomeController {
    public static HomeController getInstance() {
        return HomeControllerHolder.INSTANCE;
    }

    public Response home(Request request) {
        return ResponseFactory.getResponse(request.getRequestPath().getPath(), "../resources/templates/");
    }

    private static class HomeControllerHolder {
        private static final HomeController INSTANCE = new HomeController();
    }
}
