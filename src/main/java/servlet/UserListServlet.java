package servlet;

import db.DataBase;
import model.User;
import webserver.http.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

import java.util.List;


public class UserListServlet extends AbstractServlet {
    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        final Cookie logined = request.getCookie(LoginServlet.LOGINED);
        if (logined != null && logined.getValue().equals("true")) {
            final List<User> users = (List<User>) DataBase.findAll();
            response.setAttribute("users", users);
            response.forward("/user/list");
        } else {
            response.sendRedirect("/user/login");
        }
    }
}
