package controller;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserController extends Controller{
    public static HttpResponse signUp(HttpRequest req) {
        Database.addUser(
                new User(req.getParam("id"), req.getParam("password"), req.getParam("name"), req.getParam("email"))
        );
        return redirectTo("/index.html", req);
    }
}