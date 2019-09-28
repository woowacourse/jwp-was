package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class LoginFailedController extends AbstractController {
    private static class LoginFailedControllerLazyHolder {
        private static final LoginFailedController INSTANCE = new LoginFailedController();
    }

    public static LoginFailedController getInstance() {
        return LoginFailedControllerLazyHolder.INSTANCE;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        handle(new ModelAndView("/user/login_failed"), httpResponse);
    }
}
