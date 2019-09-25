package controller;

import db.Database;
import model.User;
import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserController {
    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_PLAIN = "text/plain";

    public static HttpResponse showSignUpPage(HttpRequest request) {
        return FileIoUtils.loadFileFromClasspath("./templates/user/form.html").map(body ->
                HttpResponse.success(request, TEXT_HTML, body)
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse signUp(HttpRequest request) {
        Database.addUser(User.of(request));

        return HttpResponse.redirection(request, TEXT_PLAIN, "/index.html");
    }
}