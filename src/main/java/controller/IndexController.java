package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class IndexController extends AbstractController {
    public static IndexController getInstance() {
        return IndexControllerLazyHolder.INSTANCE;
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("/index");
    }

    private static class IndexControllerLazyHolder {
        private static final IndexController INSTANCE = new IndexController();
    }
}
