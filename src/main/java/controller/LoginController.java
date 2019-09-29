package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Optional;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String TEXT_PLAIN = "text/plain";
    private static final String TEXT_HTML = "text/html";

    private static final String LOGIN_SUCCESS_PAGE_LOCATION = "/index.html";
    private static final String LOGIN_FAILED_PAGE_LOCATION = "/user/login_failed.html";
    private static final String LOGIN_PAGE_LOCATION = "/user/login.html";

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        return HttpResponse.successByFilePath(request, TEXT_HTML, LOGIN_PAGE_LOCATION);
    }

    @Override
    protected HttpResponse postMapping(HttpRequest request) {
        String id = request.getParam("userId");
        String password = request.getParam("password");

        Optional<User> maybeUser = Database.findUserByIdAndPassword(id, password);
        if (maybeUser.isPresent()) {
            HttpResponse response = HttpResponse.redirection(request, TEXT_PLAIN, LOGIN_SUCCESS_PAGE_LOCATION);
            String sessionId = sessionManager.setAttribute("loginUser", maybeUser.get());

            response.applySessionCookie(sessionId);
            response.applyLoginCookie(true);
            return response;
        }

        return HttpResponse.redirection(request, TEXT_PLAIN, LOGIN_FAILED_PAGE_LOCATION);
    }
}
