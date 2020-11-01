package controller;

import http.body.HttpBody;
import http.header.HttpHeader;
import http.header.HttpHeaderName;
import http.header.HttpHeaders;
import http.header.cookie.HttpCookie;
import http.header.cookie.HttpCookieOption;
import http.header.cookie.HttpCookieOptionName;
import http.header.cookie.HttpCookies;
import http.message.HttpRequestMessage;
import http.message.HttpResponseMessage;
import http.response.HttpStatus;
import http.session.HttpSessionFinder;
import service.UserService;

public class LoginController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpCookies httpCookies = HttpCookies.empty();
        HttpCookieOption loginedCookieOption = HttpCookieOption.of(HttpCookieOptionName.PATH, "/");

        HttpBody httpBody = httpRequestMessage.getHttpBody();
        if (UserService.isUser(httpBody)) {
            HttpCookie sessionCookie = HttpCookie.of("sessionId", HttpSessionFinder.createHttpSession());
            httpCookies.addCookie(sessionCookie);
            HttpCookie loginedCookie = HttpCookie.of("logined", String.valueOf(true), loginedCookieOption);
            httpCookies.addCookie(loginedCookie);

            return createLoginHttpResponseMessage(httpCookies, "/index.html");
        }

        HttpCookie loginedCookie = HttpCookie.of("logined", String.valueOf(false), loginedCookieOption);
        httpCookies.addCookie(loginedCookie);

        return createLoginHttpResponseMessage(httpCookies, "/user/login_failed.html");
    }

    private HttpResponseMessage createLoginHttpResponseMessage(HttpCookies httpCookies, String redirectUrl) {
        HttpHeaders httpHeaders = HttpHeaders.empty();

        httpCookies.getCookies().stream()
                .map(httpCookie -> HttpHeader.of(HttpHeaderName.SET_COOKIE.getName(), httpCookie.toHttpMessage()))
                .forEach(httpHeaders::addHeader);

        HttpHeader location = HttpHeader.of(HttpHeaderName.LOCATION.getName(), redirectUrl);
        httpHeaders.addHeader(location);

        return HttpResponseMessage.of(HttpStatus.FOUND, httpHeaders);
    }
}
