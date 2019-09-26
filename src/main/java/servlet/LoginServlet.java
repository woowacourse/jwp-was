package servlet;

import db.DataBase;
import model.User;
import webserver.http.Cookie;
import webserver.http.Cookies;
import webserver.http.HttpSession;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

public class LoginServlet extends AbstractServlet {
    public static final String USER_SESSION = "user";

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.forward("/user/login");
    }

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final User user = DataBase.findUserById(userId);

        if (user != null && user.matchPassword(password)) {
            final HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION, user);

            // todo 세션을 직접 쿠키에 넣지 않고 자동으로 추가하게 변경하기..
            final Cookie cookie = new Cookie(Cookies.JSESSIONID, session.getId());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            response.sendRedirect("/");
            return;
        }
        response.forward("/user/login_failed");
    }
}
