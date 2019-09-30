package controller;

import db.Database;
import model.User;
import utils.TemplateUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.httpelement.HttpContentType;
import webserver.httpelement.HttpMethod;
import webserver.httpelement.HttpSetCookie;
import webserver.router.RequestMapping;
import webserver.session.Session;
import webserver.session.SessionStorage;

import java.util.HashMap;

public class UserController extends BaseController {
    private static final String SESSION_USER_KEY = "user";

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public static HttpResponse signUp(HttpRequest req) {
        Database.addUser(
                new User(req.getParam("id"), req.getParam("password"), req.getParam("name"), req.getParam("email"))
        );
        return redirectTo("/index.html", req);
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/login")
    public static HttpResponse login(HttpRequest req) {
        return ifLoggedIn(req).map(session -> redirectTo("/index.html", req)).orElseGet(() ->
            Database.findUserById(req.getParam("id"))
                    .filter(user -> user.authenticate(req.getParam("password")))
                    .map(user ->
                        redirectTo(
                                "/index.html",
                                req,
                                HttpSetCookie.builder(
                                        Session.COOKIE_IDENTIFIER,
                                        SessionStorage.issue().setAttribute(SESSION_USER_KEY, user).getId()
                                ).path("/").build()
                        )
                    ).orElseGet(() -> redirectTo("/user/login_failed.html", req))
        );

    }

    @RequestMapping(method = HttpMethod.GET, path = "/user/list")
    public static HttpResponse list(HttpRequest req) {
        return ifLoggedIn(req).flatMap(session ->
        TemplateUtils.bake(
                "/user/list",
                new HashMap<String, Object>() {{
                    put("users", Database.findAllUsers());
                }}
        ).map(body ->
            HttpResponse.builder(HttpContentType.TEXT_HTML_UTF_8)
                        .extractFieldsFromRequest(req)
                        .body(body)
                        .build()
        )).orElseGet(() -> redirectTo("/user/login.html", req));
    }
}