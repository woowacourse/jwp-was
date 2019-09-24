package controller;

import db.Database;
import model.User;
import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.headerfields.HttpContentType;
import webserver.http.headerfields.HttpStatusCode;

public class UserController {
    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String USER_ID = "userId";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";

    public static HttpResponse showSignUpPage(HttpRequest request) {
        return FileIoUtils.loadFileFromClasspath("./templates/user/form.html").map(body ->
            HttpResponse.builder(HttpContentType.getHttpContentType(TEXT_HTML))
                        .version(request.version())
                        .connection(request.connection().orElse(null))
                        .body(body)
                        .build()
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse signUp(HttpRequest request) {
        Database.addUser(
                new User(
                        request.getParam(USER_ID),
                        request.getParam(USER_PASSWORD),
                        request.getParam(USER_NAME),
                        request.getParam(USER_EMAIL)
                )
        );
        return HttpResponse.builder(HttpContentType.getHttpContentType(TEXT_PLAIN))
                            .version(request.version())
                            .statusCode(HttpStatusCode.FOUND)
                            .connection(request.connection().orElse(null))
                            .location("/index.html")
                            .build();
    }
}