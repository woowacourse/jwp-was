package http.controller;

import db.DataBase;
import http.request.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import http.session.HttpSessionStorage;
import model.User;
import service.UserService;
import utils.HttpResponseHeaderParser;

public class UserLoginController extends Controller {

    @Override
    public HttpResponse post(HttpRequest httpRequest) {
        UserService userService = UserService.getInstance();
        boolean auth = userService.authenticateUser(httpRequest);
        String header;
        HttpSession httpSession;
        Cookie cookie = new Cookie();
        if (auth) {
            User user = DataBase.findUserById(httpRequest.getBodyValue("userId"));
            if (httpRequest.hasCookie("SESSIONID")) {
                httpSession = HttpSessionStorage.getSession(httpRequest.getSessionId());
            } else {
                httpSession = HttpSessionStorage.create();
            }
            httpSession.setAttribute("email", user.getEmail());
            cookie.setCookie("logined", "true");
            cookie.setCookie("SESSIONID", httpSession.getId());
            header = HttpResponseHeaderParser.found("/", cookie);
        } else {
            cookie.setCookie("logined", "false");
            header = HttpResponseHeaderParser.found("/user/login_failed.html", cookie);
        }
        return new HttpResponse(header);
    }
}
