package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class UserFormController extends AbstractController {
    public static UserFormController getInstance() {
        return UserFormControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        ModelAndView modelAndView = new ModelAndView("/users/form");
        handle(modelAndView, httpResponse);
    }

    private static class UserFormControllerLazyHolder {
        private static final UserFormController INSTANCE = new UserFormController();
    }
}
