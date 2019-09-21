package controller;

import db.DataBase;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import view.RedirectView;
import view.View;

public class CreateUserController extends AbstractController {

    @Override
    public View service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod().equals(HttpMethod.GET)) {
            return doGet(httpRequest, httpResponse);
        }

        return doPost(httpRequest, httpResponse);
    }

    @Override
    public View doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));
        DataBase.addUser(user);

        return new RedirectView("index.html");
    }

    @Override
    public View doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.getRequestBody("userId"),
                httpRequest.getRequestBody("password"),
                httpRequest.getRequestBody("name"),
                httpRequest.getRequestBody("email"));
        DataBase.addUser(user);

        return new RedirectView("index.html");
    }
}
