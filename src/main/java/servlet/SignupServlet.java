package servlet;

import db.DataBase;
import http.AbstractServlet;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class SignupServlet extends AbstractServlet {
    @Override
    protected HttpResponse doGet(final HttpRequest request) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.forward("./templates/user/form.html");
        return httpResponse;
    }

    @Override
    protected HttpResponse doPost(final HttpRequest request) {
        DataBase.addUser(new User(
                request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")
        ));

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.sendRedirect("/");
        return httpResponse;
    }
}
