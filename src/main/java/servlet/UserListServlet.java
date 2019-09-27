package servlet;

import db.DataBase;
import handlebars.TemplateEngine;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.ViewResolver;

import java.util.HashMap;
import java.util.Map;

import static http.HttpHeaders.COOKIE;

public class UserListServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        if (isLogin(request)) {
            Map<String, Object> model = new HashMap<>();
            model.put("users", DataBase.findAll());
            String view = TemplateEngine.applyModelInView("/user/list", model);
            ViewResolver.resolveWithBody(request, response, view.getBytes());
            return;
        }
        response.redirect("/index.html");
    }

    private boolean isLogin(HttpRequest request) {
        String cookie = request.getHeaders().getHeader(COOKIE);
        if (cookie != null && cookie.contains("logined=true")) {
            return true;
        }
        return false;
    }
}
