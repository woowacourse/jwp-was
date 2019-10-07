package webserver.pageprovider;

import db.DataBase;
import http.Cookie;
import http.Session;
import http.request.HttpRequestAccessor;
import http.response.HttpResponseAccessor;
import model.User;
import webserver.page.Page;
import webserver.page.RedirectPage;

import java.util.Optional;

public class LoginPageProvider implements PageProvider {
    @Override
    public Page provide(HttpRequestAccessor request, HttpResponseAccessor response) {
        return getAuthenticatedUser(request)
                .map(user -> loginSuccess(request, response, user))
                .orElse(RedirectPage.location("/user/login_failed.html"));
    }

    private RedirectPage loginSuccess(HttpRequestAccessor request, HttpResponseAccessor response, User user) {
        Session session = request.getSession(true).get();
        session.setAttribute("user", user);

        response.setCookie(Cookie.fromSession(session));

        return RedirectPage.location("/index.html");
    }

    private Optional<User> getAuthenticatedUser(HttpRequestAccessor request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        return Optional.ofNullable(DataBase.findUserById(userId))
                .filter(user -> user.isCorrectPassword(password));
    }
}
