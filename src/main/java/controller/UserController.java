package controller;

import db.DataBase;
import http.MediaType;
import http.request.HttpRequest;
import http.request.QueryParams;
import http.response.HttpResponse;
import http.response.ResponseBody;
import http.session.HttpCookie;
import model.User;
import view.TemplateRenderer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserController extends AbstractController {
    private static final String TRUE = "true";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        HttpCookie cookies = request.getCookies();

        if (isLogin(cookies)) {
            Map<String, Collection<User>> model = new HashMap<>();
            model.put("users", DataBase.findAll());
            String content = TemplateRenderer.renderPage(model, "list");

            ResponseBody body = new ResponseBody(content.getBytes(), MediaType.HTML);
            response.setBody(body);
            return;
        }
        response.redirect("/user/login.html");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        QueryParams queryParams = request.getQueryParams();
        User user = new User(queryParams.getParam("userId"), queryParams.getParam("password"),
                queryParams.getParam("name"), queryParams.getParam("email"));

        DataBase.addUser(user);
        response.redirect("/index.html");
    }

    private boolean isLogin(HttpCookie cookies) {
        String logined = cookies.getCookieValue("logined");
        return (logined != null) && logined.equals(TRUE);
    }
}
