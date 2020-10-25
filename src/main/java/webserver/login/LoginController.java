package webserver.login;

import webserver.controller.AbstractController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.Cookie;
import webserver.http.session.Session;
import webserver.http.session.SessionManager;
import webserver.http.session.SessionStorage;

public class LoginController extends AbstractController {
    final LoginService loginService = new LoginService();

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getBody().get("userId");
        String password = httpRequest.getBody().get("password");

        boolean login = loginService.login(userId, password);

        String location = login ? "/index.html" : "/user/login_failed.html";
        httpResponse.redirect(location);

        if (login) {
            Session session = SessionManager.getNewSession();
            SessionStorage.add(session);
            Cookie cookie = new Cookie();
            cookie.add("logined", session.getId());
            httpResponse.setCookie(cookie);
        }
    }
}
