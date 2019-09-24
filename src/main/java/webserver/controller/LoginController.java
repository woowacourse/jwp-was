package webserver.controller;

import http.request.Request;
import http.response.Response;
import model.LoginService;
import model.exception.LoginFailException;
import webserver.exception.InvalidRequestMethodException;

public class LoginController extends HttpController {
    private LoginController() {
    }

    public static LoginController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final LoginController INSTANCE = new LoginController();
    }

    @Override
    protected void doGet(Request request, Response response) {
        throw new InvalidRequestMethodException();
    }

    @Override
    protected void doPost(Request request, Response response) {
        try {
            String location = new LoginService().login(request.extractFormData());
            response.configureFoundResponse(location);
        } catch (LoginFailException e) {
            response.configureFoundResponse("/user/login_failed.html");
        }
    }
}
