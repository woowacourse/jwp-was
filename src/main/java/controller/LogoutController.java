package controller;

import javax.naming.AuthenticationException;

import http.request.Request;
import http.response.Response;
import http.session.HttpSession;
import http.session.HttpSessionStore;

public class LogoutController extends AbstractController {
    @Override
    protected void doDelete(Request request, Response response) {
        try {
            HttpSession httpSession = request.getHttpSession(false);

            if (httpSession.getAttribute("email") == null) {
                throw new AuthenticationException();
            }

            HttpSessionStore.delete(httpSession.getId());

            response.found("/index.html");
        } catch (AuthenticationException e) {
            response.found("user/login.html");
        }
    }
}
