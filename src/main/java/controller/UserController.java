package controller;

import db.Database;
import model.User;
import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserController extends AbstractController {
    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";

    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String INDEX_HTML = "/index.html";

    @Override
    public HttpResponse getMapping(HttpRequest request) {
        String filePath = FileIoUtils.convertPath("/user/form.html");

        return FileIoUtils.loadFileFromClasspath(filePath)
                .map(body -> HttpResponse.success(request, TEXT_HTML, body))
                .orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected HttpResponse postMapping(HttpRequest request) {
        Database.addUser(User.of(
                request.getParam(USER_ID),
                request.getParam(USER_PASSWORD),
                request.getParam(USER_NAME),
                request.getParam(USER_EMAIL)
        ));

        return HttpResponse.redirection(request, TEXT_PLAIN, INDEX_HTML);
    }
}