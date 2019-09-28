package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Optional;

public class LoginController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String TEXT_PLAIN = "text/plain";
    private static final String TEXT_HTML = "text/html";
    private static final String LOGIN_LOCATION = "/index.html";
    private static final String NON_LOGIN_LOCATION = "/user/login_failed.html";

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        String filePath = FileIoUtils.convertPath("/user/login.html");

        return FileIoUtils.loadFileFromClasspath(filePath)
                .map(body -> HttpResponse.success(request, TEXT_HTML, body))
                .orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected HttpResponse postMapping(HttpRequest request) {
        String id = request.getParam("userId");
        String password = request.getParam("password");

        Optional<User> maybeUser = Database.findUserByIdAndPassword(id, password);
        if (maybeUser.isPresent()) {
            HttpResponse response = HttpResponse.redirection(request, TEXT_PLAIN, LOGIN_LOCATION);
            String sessionId = sessionManager.setAttribute("loginUser", maybeUser.get());

            response.applySessionCookie(sessionId);
            response.applyLoginCookie(true);
            return response;
        }

        return HttpResponse.redirection(request, TEXT_PLAIN, NON_LOGIN_LOCATION);
    }
}
