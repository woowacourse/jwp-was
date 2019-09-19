package servlet;

import db.DataBase;
import http.AbstractServlet;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class SignupServlet extends AbstractServlet {
    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) {
        String userId = request.getBody("userId");
        String password = request.getBody("password");

        DataBase.addUser(new User(request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")));

        response.sendRedirect("/");
    }

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        response.forward("./templates/user/form.html");
    }
}
