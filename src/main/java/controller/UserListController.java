package controller;

import controller.exception.NonLoginException;
import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_PLAIN = "text/plain";

    private static final String NON_LOGIN_LOCATION = "/user/login.html";

    @Override
    protected HttpResponse getMapping(HttpRequest request) {
        try {
            Map<String, Object> users = new HashMap<>();
            users.put("users", Database.findAll());
            super.checkLogin(request);

            return templateEngine("user/list", users)
                    .map(body -> HttpResponse.successByBody(request, TEXT_HTML, body))
                    .orElse(HttpResponse.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return HttpResponse.INTERNAL_SERVER_ERROR;
        } catch (NonLoginException e) {
            return HttpResponse.redirection(request, TEXT_PLAIN, NON_LOGIN_LOCATION);
        }
    }
}
