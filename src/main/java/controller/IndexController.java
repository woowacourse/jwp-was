package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

public class IndexController extends AbstractController {
    private static class IndexControllerLazyHolder {
        private static final IndexController INSTANCE = new IndexController();
    }

    public static IndexController getInstance() {
        return IndexControllerLazyHolder.INSTANCE;
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("/index");
    }
}
