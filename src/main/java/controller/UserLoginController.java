package controller;

import domain.user.User;
import domain.user.service.UserService;
import utils.UserMapper;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

public class UserLoginController extends AbstractController {
    private static final String LOGIN_FALSE = "logined=false;";
    private static final String LOGIN_TRUE = "logined=true;";
    private static final String PATH = " Path=/";
    private static final String USER_LOGIN_FAILED_HTML = "/user/login_failed.html";
    private static final String INDEX_HTML = "/index.html";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User requestUser = UserMapper.createUser(httpRequest);
        User foundUser = UserService.findById(requestUser.getUserId());
        if (foundUser == null || foundUser.notMatchPassword(requestUser)) {
            setCookieAndRedirect(httpResponse, LOGIN_FALSE + PATH, USER_LOGIN_FAILED_HTML);
            return;
        }
        setCookieAndRedirect(httpResponse, LOGIN_TRUE + PATH, INDEX_HTML);
    }

    public void setCookieAndRedirect(HttpResponse httpResponse, String cookieValue, String path) {
        httpResponse.addHeader(ResponseHeader.SET_COOKIE, cookieValue);
        httpResponse.sendRedirect(path);
    }
}
