package controller;

import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.exception.NonLoginException;
import webserver.support.HandlebarsTemplateEngine;
import webserver.support.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);
    private static final String NOT_LOGIN_LOCATION = "/user/login.html";
    private static final String FILE_PATH_OF_USER_LIST = "user/list";
    private static final String USERS = "users";

    public static Controller getInstance() {
        return LazyHolder.userListController;
    }

    private static class LazyHolder {
        private static final Controller userListController = new UserListController();
    }

    @Override
    protected HttpResponse getMapping(HttpRequest request) {
        try {
            TemplateEngine templateEngine = new HandlebarsTemplateEngine(FILE_PATH_OF_USER_LIST);
            request.checkLogin();

            return templateEngine.apply(FILE_PATH_OF_USER_LIST, getUsers())
                    .map(body -> HttpResponse.successByBody(request, body))
                    .orElse(HttpResponse.INTERNAL_SERVER_ERROR);
        } catch (NonLoginException e) {
            return HttpResponse.redirection(request, NOT_LOGIN_LOCATION);
        }
    }

    private Map<String, Object> getUsers() {
        Map<String, Object> users = new HashMap<>();

        users.put(USERS, Database.findAll());
        return users;
    }
}
