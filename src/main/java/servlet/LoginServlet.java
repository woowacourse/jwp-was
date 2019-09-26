package servlet;

import db.DataBase;
import model.User;
import webserver.http.Cookie;
import webserver.http.HttpSession;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

public class LoginServlet extends AbstractServlet {
    public static final String LOGINED = "logined";
    private static final String USER = "user";

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.forward("/user/login");
    }

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final User user = DataBase.findUserById(userId);

//        final HttpSession session = request.getSession();
//        if (user != null && user.matchPassword(password)) {
//           session.setAttribute(USER, user);
//           response.sendRedirect("/");
//        } else {
//            response.sendRedirect("/user/login_failed.html");
//        }

        // todo 리팩토링
        if (user != null && user.matchPassword(password)) {
            final Cookie cookie = new Cookie(LOGINED, "true");
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            response.sendRedirect("/");
        } else {
            response.forward("/user/login_failed");
        }
    }
}
