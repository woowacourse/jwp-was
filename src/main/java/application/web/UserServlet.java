package application.web;

import application.db.DataBase;
import application.model.User;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import webserver.HttpServlet;

import java.util.Map;

public class UserServlet extends HttpServlet {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        Map<String, String> formData = request.getFormData();
        User user = new User(formData.get("userId"), formData.get("password"), formData.get("name"), formData.get("email"));
        DataBase.addUser(user);

        response.addHeader("Location", "/index.html");
        response.response(HttpStatus.FOUND);
    }
}
