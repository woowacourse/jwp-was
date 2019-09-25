package webserver.controller;

import exception.UnregisteredURLException;
import exception.UnsupportedMethodException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

public class IndexController extends AbstractController {
    private IndexController() { }

    public static IndexController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final IndexController INSTANCE = new IndexController();
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.responseOK(httpRequest);
        } catch (Exception e) {
            httpResponse.responseBadRequest(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new UnregisteredURLException();
    }
}
