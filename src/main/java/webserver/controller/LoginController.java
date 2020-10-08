package webserver.controller;

import webserver.http.body.HttpBody;
import webserver.http.header.HttpCookieOption;
import webserver.http.header.HttpHeaderField;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.response.HttpStatus;
import webserver.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.header.HttpHeader.HEADER_VALUE_DELIMITER;

public class LoginController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpBody httpBody = httpRequestMessage.getHttpBody();
        if (UserService.isUser(httpBody)) {
            return createLoginHttpResponseMessage(true, "/index.html");
        }

        return createLoginHttpResponseMessage(false, "/user/login_failed.html");
    }

    private HttpResponseMessage createLoginHttpResponseMessage(boolean isLogined, String redirectUrl) {
        String loginCookie = "logined=" + isLogined;
        String path = HttpCookieOption.PATH.toHttpMessage("/");

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaderField.SET_COOKIE.getName(), loginCookie + HEADER_VALUE_DELIMITER + path);
        headers.put(HttpHeaderField.LOCATION.getName(), redirectUrl);

        return HttpResponseMessage.of(HttpStatus.FOUND, headers);
    }
}
