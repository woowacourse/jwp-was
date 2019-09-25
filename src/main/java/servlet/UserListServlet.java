package servlet;

import webserver.http.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

public class UserListServlet extends AbstractServlet {
    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        final Cookie logined = request.getCookie(LoginServlet.LOGINED);
        if (logined != null && logined.getValue().equals("true")) {
            response.forward("./templates/user/list.html");
        } else {
            response.sendRedirect("/user/login");
        }
    }
}
