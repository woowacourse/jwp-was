package controller;

import db.DataBase;
import model.Request;
import model.Response;
import model.User;

public class CreateUserController extends AbstractController {
    @Override
    void doPost(Request request, Response response) {
        String url = request.getUrl();
        String extension = url.substring(url.lastIndexOf(".") + 1);

        String classPath = "./templates" + url;

        if (!"html".equals(extension)) {
            classPath = "./static" + url;
        }

        if (url.contains("/user/create")) {
            saveUser(request);
            response.response300(request.getHeader("Origin") + "/index.html");
        }
    }

    @Override
    void doGet(Request request, Response response) {
    }

    private void saveUser(Request request) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        DataBase.addUser(user);
    }
}
