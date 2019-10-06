package webserver.controller;

import db.DataBase;
import model.User;
import webserver.ModelAndView;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;
import webserver.controller.session.HttpSessionManager;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class LoginController extends AbstractController {
    public static final String LOGIN_SUCCESS_INDEX = "/index.html";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGIN_FAILED_INDEX = "/user/login_failed.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.applyTemplateEngine(httpRequest.getPath());
        return HttpResponse.ok(httpRequest, modelAndView.getView());
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Map<String, String> requestBodyFields = httpRequest.getBodyFields();
        String userId = requestBodyFields.get(USER_ID);
        String password = requestBodyFields.get(PASSWORD);
        Optional<User> maybeUser = DataBase.findUserById(userId);
        return loginRedirect(httpRequest, maybeUser, password);
    }

    private HttpResponse loginRedirect(HttpRequest httpRequest, Optional<User> maybeUser, String password) {
        if (maybeUser.isPresent()) {
            return LoginSuccessRedirect(httpRequest, password, maybeUser);
        }
        return HttpResponse.sendRedirect(httpRequest, LOGIN_FAILED_INDEX);
    }

    private HttpResponse LoginSuccessRedirect(HttpRequest httpRequest, String password, Optional<User> maybeUser) {
        User user = maybeUser.get();
        if (user.isMatchPassword(password)) {
            HttpSessionManager httpSessionManager = HttpSessionManager.getInstance();
            String sessionId = httpSessionManager.setAttribute("user", user);
            httpRequest.setSessionId(sessionId);
            return HttpResponse.loginSuccessRedirect(httpRequest, LOGIN_SUCCESS_INDEX);
        }
        return HttpResponse.sendRedirect(httpRequest, LOGIN_FAILED_INDEX);
    }
}
