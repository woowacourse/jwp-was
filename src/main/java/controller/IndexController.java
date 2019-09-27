package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class IndexController extends AbstractController {
    private static class IndexControllerLazyHolder {
        private static final IndexController INSTANCE = new IndexController();
    }

    public static IndexController getInstance() {
        return IndexControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        handle(new ModelAndView("/index"), httpResponse);
    }
}
