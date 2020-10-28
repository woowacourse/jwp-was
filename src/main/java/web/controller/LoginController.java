package web.controller;

import db.DataBase;
import model.User;
import web.HttpRequest;
import web.HttpResponse;

public class LoginController extends AbstractController {
    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(
            request.getRequestBody().getParameter("userId")
        );

        if (user != null && user.login(request.getRequestBody().getParameter("password"))) {
            response.addHeader("Set-Cookie", "logined=true");
            response.sendRedirect("/index.html");
        }
        response.sendRedirect("/user/login_failed.html");
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {

    }
}
