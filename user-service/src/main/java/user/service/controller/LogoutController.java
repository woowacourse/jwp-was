package user.service.controller;

import javax.naming.AuthenticationException;

import http.was.controller.annotation.Controller;
import http.was.controller.annotation.RequestMapping;
import http.was.http.HttpMethod;
import http.was.http.request.Request;
import http.was.http.response.Response;
import http.was.http.session.HttpSession;
import http.was.http.session.HttpSessionStore;

@Controller
public class LogoutController {
    @RequestMapping(path = "/user/logout", method = HttpMethod.DELETE)
    public static void doDelete(Request request, Response response) {
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
