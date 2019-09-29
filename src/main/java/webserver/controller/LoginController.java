package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ModelAndView;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGIN_SUCCESS_INDEX = "/index.html";
    private static final String LOGIN_FAILED_INDEX = "/user/login_failed.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.applyTemplateEngine(httpRequest.getPath());
        return HttpResponse.ok(httpRequest,modelAndView.getView());
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        Map<String, String> requestBodyFields = httpRequest.getBodyFields();
        String userId = requestBodyFields.get(USER_ID);
        String password = requestBodyFields.get(PASSWORD);
        logger.debug(">>> {} , {}", userId, password);
        return sendLoginPage(httpRequest, userId, password);
    }

    private HttpResponse sendLoginPage(HttpRequest httpRequest,String userId, String password) {
        Optional<User> maybeUser = DataBase.findUserById(userId);

        return maybeUser.filter(user -> user.isMatchPassword(password))
            .map(user -> HttpResponse.sendRedirect(httpRequest,LOGIN_SUCCESS_INDEX, true))
            .orElse(HttpResponse.sendRedirect(httpRequest, LOGIN_FAILED_INDEX, false));
    }
}
