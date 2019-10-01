package servlet;

import db.DataBase;
import model.User;
import webserver.http.HttpSession;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

import java.util.List;

import static servlet.LoginServlet.USER_SESSION;


public class UserListServlet extends AbstractServlet {
    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        final HttpSession session = request.getSession();
        if (session.getAttribute(USER_SESSION) != null) {
            final List<User> users = DataBase.findAll();
            response.setAttribute("users", users);
            response.forward("/user/list");
            return;
        }
        response.sendRedirect("/user/login");
    }
}
