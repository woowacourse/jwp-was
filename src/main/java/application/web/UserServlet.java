package application.web;

import application.db.DataBase;
import application.model.User;
import http.HttpRequest;
import webserver.Servlet;

import java.util.Map;

public class UserServlet extends Servlet {

    @Override
    public void doPost(HttpRequest request) {
        Map<String, String> formData = request.getFormData();
        User user = new User(formData.get("userId"), formData.get("password"), formData.get("name"), formData.get("email"));
        DataBase.addUser(user);
    }
}
