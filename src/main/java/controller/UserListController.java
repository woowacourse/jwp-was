package controller;

import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.exception.NonLoginException;
import webserver.http.headerfields.MimeType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private static final String NOT_LOGIN_LOCATION = "/user/login.html";

    public static Controller getInstance() {
        return LazyHolder.userListController;
    }

    private static class LazyHolder {
        private static final Controller userListController = new UserListController();
    }

    @Override
    protected HttpResponse getMapping(HttpRequest request) {
        try {
            Map<String, Object> users = new HashMap<>();
            users.put("users", Database.findAll());
            request.checkLogin();

            return templateEngine("user/list", users)
                    .map(body -> HttpResponse.successByBody(request, MimeType.TEXT_HTML, body))
                    .orElse(HttpResponse.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return HttpResponse.INTERNAL_SERVER_ERROR;
        } catch (NonLoginException e) {
            return HttpResponse.redirection(request, MimeType.TEXT_PLAIN, NOT_LOGIN_LOCATION);
        }
    }
}
