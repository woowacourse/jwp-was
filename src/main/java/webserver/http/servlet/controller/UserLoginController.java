package webserver.http.servlet.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractView;
import webserver.http.servlet.RedirectView;

public class UserLoginController implements Controller {
    @Override
    public AbstractView doService(HttpRequest request, HttpResponse response) {
        response.setCookie("logined=false", "/");
        return new RedirectView("/user/login_failed.html");
    }
}
