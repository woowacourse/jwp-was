package controller;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.httpelement.HttpMethod;
import webserver.router.RequestMapping;

public class UserController extends BaseController {
    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public static HttpResponse signUp(HttpRequest req) {
        Database.addUser(
                new User(req.getParam("id"), req.getParam("password"), req.getParam("name"), req.getParam("email"))
        );
        return redirectTo("/index.html", req);
    }
}