package controller;

import static http.HttpMethod.*;

import java.util.Arrays;
import java.util.List;

import annotation.RequestMapping;
import db.DataBase;
import http.HttpBody;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.User;

@RequestMapping(path = "/user")
public class UserController extends AbstractController {
    private static final List<HttpMethod> ALLOWED_METHODS = Arrays.asList(GET, DELETE, PUT);

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setMethodNotAllowed(ALLOWED_METHODS);
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpBody httpBody = httpRequest.getBody();
        User user = new User(
            httpBody.get("userId"),
            httpBody.get("password"),
            httpBody.get("name"),
            httpBody.get("email"));
        DataBase.addUser(user);
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.addHeader("Location", "/index.html");
    }

    @Override
    protected void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setMethodNotAllowed(ALLOWED_METHODS);
    }

    @Override
    protected void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setMethodNotAllowed(ALLOWED_METHODS);
    }
}
