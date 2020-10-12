package webserver.controller;

import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;
import webserver.http.header.HttpHeaderName;
import webserver.http.header.HttpHeaders;
import webserver.http.header.cookie.HttpCookie;
import webserver.http.header.cookie.HttpCookieOption;
import webserver.http.header.cookie.HttpCookieOptionName;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.response.HttpStatus;
import webserver.http.session.HttpSessionFinder;
import webserver.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class LoginController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        List<HttpCookie> httpCookies = new ArrayList<>();
        HttpCookieOption loginedCookieOption = HttpCookieOption.of(HttpCookieOptionName.PATH, "/");

        HttpBody httpBody = httpRequestMessage.getHttpBody();
        if (UserService.isUser(httpBody)) {
            HttpCookie sessionCookie = HttpCookie.of("sessionId", HttpSessionFinder.createHttpSession());
            httpCookies.add(sessionCookie);
            HttpCookie loginedCookie = HttpCookie.of("logined", String.valueOf(true), loginedCookieOption);
            httpCookies.add(loginedCookie);

            return createLoginHttpResponseMessage(httpCookies, "/index.html");
        }

        HttpCookie loginedCookie = HttpCookie.of("logined", String.valueOf(false), loginedCookieOption);
        httpCookies.add(loginedCookie);

        return createLoginHttpResponseMessage(httpCookies, "/user/login_failed.html");
    }

    private HttpResponseMessage createLoginHttpResponseMessage(List<HttpCookie> httpCookies, String redirectUrl) {
        HttpHeaders httpHeaders = HttpHeaders.empty();

        httpCookies.stream()
                .map(httpCookie -> HttpHeader.of(HttpHeaderName.SET_COOKIE.getName(), httpCookie.toHttpMessage()))
                .forEach(httpHeaders::addHeader);

        HttpHeader location = HttpHeader.of(HttpHeaderName.LOCATION.getName(), redirectUrl);
        httpHeaders.addHeader(location);

        return HttpResponseMessage.of(HttpStatus.FOUND, httpHeaders);
    }
}
