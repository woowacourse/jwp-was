package controller;

import db.DataBase;
import http.common.HttpHeader;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpUriParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.Response302;
import http.response.StatusLine;
import model.User;

public class UserLoginController implements Controller {

    private static final String LOGIN_PATH = "/index.html";
    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";
    private static final RequestMapping LOGIN_REQUEST_MAPPING = RequestMapping.of(HttpMethod.POST, HttpUriParser.parse("/user/login"));

    @Override
    public HttpResponse service(final HttpRequest httpRequest) {
        String userId = httpRequest.findBodyParam("userId");
        String password = httpRequest.findBodyParam("password");

        User user = DataBase.findUserById(userId);

        StatusLine statusLine = new StatusLine(httpRequest.getHttpVersion(), HttpStatus.FOUND);
        HttpHeader responseHeader = new HttpHeader();

        if (user == null || !user.isPasswordEquals(password)) {
            responseHeader.putHeader("Location", LOGIN_FAIL_PATH);
            responseHeader.putHeader("Set-Cookie", "logined=false");
            return new Response302(statusLine, responseHeader, null);
        }

        responseHeader.putHeader("Location", LOGIN_PATH);

        responseHeader.putHeader("Set-Cookie", "logined=true; Path=/"); // 1
        responseCookie.setCookie(새로운 쿠키);       // 2

        return new Response302(statusLine, responseHeader, null);
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return LOGIN_REQUEST_MAPPING.equals(requestMapping);
    }
}
