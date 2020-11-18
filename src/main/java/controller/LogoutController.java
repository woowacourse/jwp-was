package controller;

import http.request.Request;
import http.response.Response;
import http.session.HttpSession;
import http.session.HttpSessionStore;

import javax.naming.AuthenticationException;

public class LogoutController extends AbstractController {
    @Override
    protected void doDelete(Request request, Response response) {
        try {
            HttpSession httpSession = request.getSession();

            if (httpSession == null || !HttpSessionStore.isContains(httpSession)) {
                throw new AuthenticationException();
            }
            HttpSessionStore.delete(httpSession.getId());

            response.found("/index.html");
        } catch (AuthenticationException e) {
            response.found("user/login.html");
        }
    }
}
