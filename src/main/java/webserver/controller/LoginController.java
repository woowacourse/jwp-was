package webserver.controller;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.Cookie;
import webserver.response.HttpResponse;

public class LoginController extends AbstractController{

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String userId = httpRequest.getBodyParameter("userId");
        String password = httpRequest.getBodyParameter("password");
        User user = DataBase.findUserById(userId);

        if(user.isSamePassword(password)) {
            Cookie cookie = new Cookie("logined", "true");
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            httpResponse.sendRedirect("/index.html");
            return;
        }
        Cookie cookie = new Cookie("logined", "false");
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
        httpResponse.sendRedirect("/user/login_failed.html");
    }
}
