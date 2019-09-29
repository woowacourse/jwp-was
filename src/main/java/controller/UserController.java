package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.QueryParams;
import http.response.HttpResponse;
import model.User;

public class UserController extends AbstractController {
    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        QueryParams queryParams = request.getQueryParams();
        User user = new User(queryParams.getParam("userId"), queryParams.getParam("password"),
                queryParams.getParam("name"), queryParams.getParam("email"));

        DataBase.addUser(user);
        response.redirect("/index.html");
    }
}
