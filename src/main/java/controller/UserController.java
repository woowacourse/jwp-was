package controller;

import annotation.RequestMapping;
import db.DataBase;
import http.HttpBody;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.User;

@RequestMapping(path = "/user")
public class UserController extends AbstractController {
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
}
