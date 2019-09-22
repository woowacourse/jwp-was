package controller;

import db.DataBase;
import http.common.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.Response302;
import http.response.StatusLine;
import model.User;

public class UserCreateController extends Controllers {

    HttpResponse doPost(HttpRequest httpRequest) {
        String userId = httpRequest.findParam("userId");
        String password = httpRequest.findParam("password");
        String name = httpRequest.findParam("name");
        String email = httpRequest.findParam("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        StatusLine statusLine = new StatusLine(httpRequest.getHttpVersion(), HttpStatus.FOUND);
        HttpHeader responseHeader = new HttpHeader();
        responseHeader.putHeader("Location", "/index.html");

        return new Response302(statusLine, responseHeader, null);
    }
}
