package webserver.servlet;

import java.util.Map;

import db.DataBase;
import model.User;
import webserver.domain.request.HttpRequest;

public class UserCreate implements Servlet{
    @Override
    public void service(HttpRequest httpRequest) {
        Map<String, String> parameters = httpRequest.getParameters();
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            return;
        }

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }

    @Override
    public String getResourcePathToRedirect() {
        return "./templates/index.html";
    }
}
