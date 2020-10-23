package controller;

import http.request.Request;
import http.response.Response;
import http.session.HttpSessionStore;

import javax.naming.AuthenticationException;

public class LogoutController extends AbstractController {
    @Override
    protected void doDelete(Request request, Response response) {
        try {
            String jsessionid = request.getCookie("JSESSIONID");

            if (!HttpSessionStore.isContains(jsessionid)) {
                throw new AuthenticationException();
            }
            HttpSessionStore.delete(jsessionid);

            response.found("/index.html");
        } catch (AuthenticationException e) {
            response.found("user/login.html");
        }
    }
}
