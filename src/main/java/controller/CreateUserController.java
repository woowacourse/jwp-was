package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserController extends Controller {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    public static final String PATH = "/user/create";

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.debug("Saved UserId: {}", httpRequest.getQueryValue("userId"));
        save(httpRequest.getQueryValue("userId"), httpRequest.getQueryValue("password"),
                httpRequest.getQueryValue("name"), httpRequest.getQueryValue("email"));
        httpResponse.redirect("index.html");
    }

    private void save(String userId, String password, String name, String email) {
        if (DataBase.findUserById(userId) == null) {
            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            return;
        }

        throw new IllegalArgumentException();
    }
}
