package controller;

import db.DataBase;
import http.MediaType;
import http.request.HttpRequest;
import http.request.QueryParams;
import http.response.HttpResponse;
import http.response.ResponseBody;
import http.session.HttpCookie;
import model.User;
import view.ViewResolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserController extends AbstractController {
    private static final String TRUE = "true";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        HttpCookie cookies = request.getCookies();

        if (!isLogin(cookies)) {
            response.redirect("/user/login.html");
            return;
        }

        // TODO: 2019-10-08 리펙토링
        Map<String, Collection<User>> model = new HashMap<>();
        model.put("users", DataBase.findAll());
        response.setBody(getResponseBody(model));
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
        return TRUE.equals(logined);
    }

    private ResponseBody getResponseBody(Map<String, Collection<User>> model) {
        String content = ViewResolver.resolve(model, "list");
        return new ResponseBody(content.getBytes(), MediaType.HTML);
    }
}
