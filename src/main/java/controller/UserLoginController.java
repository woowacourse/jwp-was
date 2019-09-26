package controller;

import db.DataBase;
import http.common.CookieParser;
import http.common.Cookies;
import http.common.HttpHeader;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpUriParser;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.StatusLine;
import model.User;

import static java.util.Objects.nonNull;

public class UserLoginController implements Controller {

    private static final String LOGIN_PATH = "/index.html";
    private static final String LOGIN_FAIL_PATH = "/user/login_failed.html";
    private static final RequestMapping LOGIN_REQUEST_MAPPING = RequestMapping.of(HttpMethod.POST, HttpUriParser.parse("/user/login"));

    @Override
    public void service(final HttpRequest httpRequest,final HttpResponse httpResponse) {
        String userId = httpRequest.findBodyParam("userId");
        String password = httpRequest.findBodyParam("password");

        User user = DataBase.findUserById(userId);

        httpResponse.setStatusLine(new StatusLine(httpRequest.getHttpVersion(), HttpStatus.FOUND));
        HttpHeader responseHeader = new HttpHeader();

        if (nonNull(user) && user.isPasswordEquals(password)) {
            responseHeader.putHeader("Location", LOGIN_PATH);
            Cookies cookies = Cookies.create().addCookie(CookieParser.parse("logined=true; Path=/"));

            httpResponse.setResponseHeader(responseHeader);
            httpResponse.setCookies(cookies);
            return;
        }

        responseHeader.putHeader("Location", LOGIN_FAIL_PATH);
        Cookies cookies = Cookies.create().addCookie(CookieParser.parse("logined=false"));

        httpResponse.setResponseHeader(responseHeader);
        httpResponse.setCookies(cookies);
    }

    @Override
    public boolean isMapping(final RequestMapping requestMapping) {
        return LOGIN_REQUEST_MAPPING.equals(requestMapping);
    }
}
