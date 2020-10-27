package http.controller;

import http.request.HttpRequest;
import http.session.HttpSession;
import http.session.HttpSessionStorage;

public abstract class AuthController extends Controller {
    protected HttpSession retrieveHttpSession(HttpRequest httpRequest) {
        if (httpRequest.hasCookie("SESSIONID")) {
            HttpSession httpSession = HttpSessionStorage.getSession(httpRequest.getSessionId());
            if (httpSession == null) {
                httpSession = HttpSessionStorage.create();
            }
            return httpSession;
        } else {
            return HttpSessionStorage.create();
        }
    }
}
