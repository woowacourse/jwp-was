package webserver.controller;

import webserver.http.body.HttpBody;
import webserver.http.header.HttpCookie;
import webserver.http.header.HttpCookieOption;
import webserver.http.header.HttpHeaderField;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.response.HttpStatus;
import webserver.http.session.HttpSessionFinder;
import webserver.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class LoginController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpBody httpBody = httpRequestMessage.getHttpBody();

        if (UserService.isUser(httpBody)) {
            HttpCookie cookie = new HttpCookie.Builder()
                    .addCookie("sessionId", HttpSessionFinder.createHttpSession())
                    .addCookie("logined", String.valueOf(true))
                    .addCookie(HttpCookieOption.PATH.getName(), "/")
                    .build();
            return createLoginHttpResponseMessage(cookie, "/index.html");
        }

        HttpCookie cookie = new HttpCookie.Builder()
                .addCookie("logined", String.valueOf(false))
                .addCookie(HttpCookieOption.PATH.getName(), "/")
                .build();

        return createLoginHttpResponseMessage(cookie, "/user/login_failed.html");
    }

    private HttpResponseMessage createLoginHttpResponseMessage(HttpCookie cookie, String redirectUrl) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaderField.SET_COOKIE.getName(), cookie.toHttpMessage());
        headers.put(HttpHeaderField.LOCATION.getName(), redirectUrl);

        return HttpResponseMessage.of(HttpStatus.FOUND, headers);
    }
}
