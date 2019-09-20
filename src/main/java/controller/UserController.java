package controller;

import db.Database;
import model.User;
import utils.io.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.headerfields.HttpContentType;
import webserver.http.headerfields.HttpStatusCode;

public class UserController {
    public static HttpResponse showSignUpPage(HttpRequest req) {
        return FileIoUtils.loadFileFromClasspath("./templates/user/form.html").map(body ->
            HttpResponse.builder(HttpContentType.TEXT_HTML())
                        .version(req)
                        .connection(req)
                        .body(body)
                        .build()
        ).orElse(HttpResponse.INTERNAL_SERVER_ERROR);
    }

    public static HttpResponse signUp(HttpRequest req) {
        Database.addUser(
                new User(
                        req.getParam("userId"),
                        req.getParam("password"),
                        req.getParam("name"),
                        req.getParam("email")
                )
        );
        return HttpResponse.builder(HttpContentType.TEXT_PLAIN())
                            .version(req)
                            .statusCode(HttpStatusCode.FOUND)
                            .connection(req)
                            .location("/index.html")
                            .build();
    }
}