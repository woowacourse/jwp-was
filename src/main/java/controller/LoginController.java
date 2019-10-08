package controller;

import db.Database;
import utils.ControllerUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.env.Env;
import webserver.env.RequestMapping;
import webserver.env.Session;
import webserver.env.Singleton;
import webserver.httpelement.HttpMethod;
import webserver.httpelement.HttpSetCookie;

@Singleton
public final class LoginController implements Controller {
    private static final String SESSION_USER_KEY = "user";

    @Override
    @RequestMapping(method = HttpMethod.POST, path = "/user/login")
    public HttpResponse handle(HttpRequest req) {
        return ControllerUtils.ifLoggedIn(req).map(session ->
                ControllerUtils.redirectTo("/index.html", req)
        ).orElseGet(() ->
            Database.findUserById(req.getParam("id"))
                    .filter(user -> user.authenticate(req.getParam("password")))
                    .map(user ->
                        ControllerUtils.redirectTo(
                                "/index.html",
                                req,
                                HttpSetCookie.builder(
                                        Session.COOKIE_IDENTIFIER,
                                        Env.sessionStorage().issue().setAttribute(SESSION_USER_KEY, user).getId()
                                ).path("/").build()
                        )
                    ).orElseGet(() -> ControllerUtils.redirectTo("/user/login_failed.html", req))
        );
    }
}