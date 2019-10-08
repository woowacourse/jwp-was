package controller;

import db.Database;
import utils.ControllerUtils;
import utils.TemplateUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.env.RequestMapping;
import webserver.env.Singleton;
import webserver.httpelement.HttpContentType;
import webserver.httpelement.HttpMethod;

import java.util.HashMap;

@Singleton
public final class UserListController implements Controller {
    @Override
    @RequestMapping(method = HttpMethod.GET, path = "/user/list")
    public HttpResponse handle(HttpRequest req) {
        return ControllerUtils.ifLoggedIn(req).flatMap(session ->
        TemplateUtils.bake(
                "/user/list",
                new HashMap<String, Object>() {{
                    this.put("users", Database.findAllUsers());
                }}
        ).map(body ->
            HttpResponse.builder(HttpContentType.TEXT_HTML_UTF_8)
                        .extractFieldsFromRequest(req)
                        .body(body)
                        .build()
        )).orElseGet(() -> ControllerUtils.redirectTo("/user/login.html", req));
    }
}