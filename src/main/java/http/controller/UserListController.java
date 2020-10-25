package http.controller;

import java.util.Collection;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestMapping;
import http.response.HttpResponse;
import model.User;
import view.Model;
import view.View;

public class UserListController extends HttpRequestMappingAbstractController {

    private static final String LOGIN_SUCCESS = "true";
    private static final String LOGINED = "logined";

    public UserListController(HttpRequestMapping httpRequestMapping) {
        super(httpRequestMapping);
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {

        if (isLoggedin(httpRequest)) {
            Model model = new Model();
            Collection<User> users = DataBase.findAll();
            model.addAttributes("users", users);
            httpResponse.ok(new View("/user/list"), model);
            return;
        }

        httpResponse.redirect("/index.html");
    }

    private boolean isLoggedin(HttpRequest httpRequest) {
        return httpRequest.hasCookie() && LOGIN_SUCCESS.equals(httpRequest.getCookie(LOGINED));
    }
}
