package servlet;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

public class LoginServlet extends AbstractServlet {
    public static final String LOGINED = "logined";

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final User user = DataBase.findUserById(userId);

        // todo 리팩토링
        if (user != null && user.matchPassword(password)) {
            response.setCookie(LOGINED, true);
            response.setCookie("Path", "/");
            response.sendRedirect("/");
        } else {
            response.setCookie(LOGINED, false);
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
