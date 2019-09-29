package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

public class UserFormController extends AbstractController {
    public static UserFormController getInstance() {
        return UserFormControllerLazyHolder.INSTANCE;
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("/user/form");
    }

    private static class UserFormControllerLazyHolder {
        private static final UserFormController INSTANCE = new UserFormController();
    }
}
