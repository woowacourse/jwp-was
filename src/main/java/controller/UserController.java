package controller;

import db.Database;
import model.User;
import utils.io.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.http.HttpContentType;
import webserver.http.HttpStatusCode;

public class UserController {
    public static HttpResponse showSignUpPage(HttpRequest req) {
        return FileIoUtils.loadFileFromClasspath("static/user/form.html").map(body ->
            HttpResponse.builder(HttpContentType.TEXT_HTML_UTF_8)
                        .extractFromRequest(req)
                        .body(body)
                        .build()
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse signUp(HttpRequest req) {
        Database.addUser(
                new User(
                        req.getParam("id"),
                        req.getParam("password"),
                        req.getParam("name"),
                        req.getParam("email")
                )
        );
        return HttpResponse.builder(HttpContentType.TEXT_PLAIN_UTF_8)
                            .extractFromRequest(req)
                            .statusCode(HttpStatusCode.FOUND)
                            .location("/index.html")
                            .build();
    }
}