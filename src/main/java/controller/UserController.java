package controller;

import db.Database;
import model.User;
import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserController {
    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String INDEX_HTML = "/index.html";

    public static HttpResponse showSignUpPage(HttpRequest request) {
        String filePath = FileIoUtils.convertPath("/user/form.html");

        return FileIoUtils.loadFileFromClasspath(filePath).map(body ->
                HttpResponse.success(request, TEXT_HTML, body)
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse signUp(HttpRequest request) {
        Database.addUser(User.of(request));

        return HttpResponse.redirection(request, TEXT_PLAIN, INDEX_HTML);
    }
}