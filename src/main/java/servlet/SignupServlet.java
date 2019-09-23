package servlet;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.servlet.AbstractServlet;

public class SignupServlet extends AbstractServlet {

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) {
        DataBase.addUser(new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")));

        response.sendRedirect("/");
    }

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.forward("./templates/user/form.html");
    }
}