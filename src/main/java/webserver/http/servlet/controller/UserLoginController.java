package webserver.http.servlet.controller;

import static java.util.Objects.*;

import db.DataBase;
import model.User;
import webserver.http.request.HttpParams;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractView;
import webserver.http.servlet.RedirectView;

public class UserLoginController implements Controller {
    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        String userId = HttpParams.of(request.getBody()).get("userId");
        String password = HttpParams.of(request.getBody()).get("password");
        User user = DataBase.findUserById(userId);
        if (isNull(user) || user.isPasswordNotEquals(password)) {
            response.setCookie("logined=false", "/");
            return new RedirectView("/user/login_failed.html");
        }
        request.setCookie("logined=true");
        response.setCookie("logined=true", "/");
        return new RedirectView("/index.html");
    }
}
