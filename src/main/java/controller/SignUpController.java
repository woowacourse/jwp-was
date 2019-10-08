package controller;

import db.Database;
import model.User;
import utils.ControllerUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.env.RequestMapping;
import webserver.env.Singleton;
import webserver.httpelement.HttpMethod;

@Singleton
public final class SignUpController implements Controller {
    @Override
    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public HttpResponse handle(HttpRequest req) {
        Database.addUser(
                new User(req.getParam("id"), req.getParam("password"), req.getParam("name"), req.getParam("email"))
        );
        return ControllerUtils.redirectTo("/index.html", req);
    }
}