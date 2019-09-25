package servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

public class UserListServlet extends AbstractServlet {
    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        final String logined = request.getCookie(LoginServlet.LOGINED);
        if (logined != null && logined.equals("true")) {
            response.forward("./templates/user/list.html");
        } else {
            response.sendRedirect("/user/login");
        }
    }
}
