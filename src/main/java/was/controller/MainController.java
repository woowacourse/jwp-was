package was.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class MainController extends AbstractController {
    private MainController() {}

    public static MainController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final MainController INSTANCE = new MainController();
    }

    @Override
    void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.forward("/index.html");
    }
}
