package controller;

import domain.user.User;
import domain.user.service.UserService;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

public class UserLoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = UserService.findUser(httpRequest);
        if (user == null) {
            httpResponse.addHeader(ResponseHeader.SET_COOKIE, "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        httpResponse.addHeader(ResponseHeader.SET_COOKIE, "logined=true; Path=/");
        httpResponse.sendRedirect("/index.html");
    }
}
