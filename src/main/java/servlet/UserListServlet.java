package servlet;

import db.DataBase;
import handlebars.TemplateEngine;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.ViewResolver;

import java.util.HashMap;
import java.util.Map;

public class UserListServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (request.matchCookie("logined", "true")) {
            Map<String, Object> model = new HashMap<>();
            model.put("users", DataBase.findAll());
            String view = TemplateEngine.applyModelInView("/user/list", model);
            ViewResolver.resolveWithBody(request, response, view.getBytes());
            return;
        }
        response.redirect("/index.html");
    }
}
