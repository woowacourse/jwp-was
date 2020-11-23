package http.controller;

import http.request.Cookie;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSessionStorage;
import utils.HttpResponseHeaderParser;

public class UserLogoutController extends AuthController {
    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        Cookie cookie = new Cookie();
        cookie.setCookie("logined", "false");
        HttpSessionStorage.deleteSession(httpRequest.getSessionId());
        String header = HttpResponseHeaderParser.found("/", cookie);
        return new HttpResponse(header);
    }
}
