package http.controller;

import db.DataBase;
import http.request.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import model.User;
import service.UserService;
import utils.HttpResponseHeaderParser;

public class UserLoginController extends AuthController {

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        UserService userService = UserService.getInstance();
        boolean auth = userService.authenticateUser(httpRequest);
        HttpSession httpSession;
        Cookie cookie = new Cookie();
        if (auth) {
            User user = DataBase.findUserById(httpRequest.getBodyValue("userId"));
            httpSession = retrieveHttpSession(httpRequest);
            httpSession.setAttribute("email", user.getEmail());
            cookie.setCookie("logined", "true");
            cookie.setCookie("SESSIONID", httpSession.getId());
            return new HttpResponse(HttpResponseHeaderParser.found("/", cookie));
        }
        cookie.setCookie("logined", "false");
        return new HttpResponse(HttpResponseHeaderParser.found("/user/login_failed.html", cookie));
    }
}
