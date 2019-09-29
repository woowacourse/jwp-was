package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;

public class LoginFailedController extends AbstractController {
    private static class LoginFailedControllerLazyHolder {
        private static final LoginFailedController INSTANCE = new LoginFailedController();
    }

    public static LoginFailedController getInstance() {
        return LoginFailedControllerLazyHolder.INSTANCE;
    }

    @Override
    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView("/user/login_failed");
    }
}
