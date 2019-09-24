package controller;

import db.DataBase;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

import java.util.Map;

public class SignUpController extends AbstractController {

    public static final String USER_CREATE_URL = "/user/create";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOCATION_HEADER_KEY = "Location";

    public void doGet(HttpRequest req, HttpResponse res) {
        throw createUnsupportedException();
    }

    @Override
    public void doPost(HttpRequest req, HttpResponse res) {
        Map<String, String> parsedBody = (Map<String, String>) req.getBody();
        User user = new User(parsedBody.get(USER_ID),
            parsedBody.get(PASSWORD),
            parsedBody.get(NAME),
            parsedBody.get(EMAIL));
        DataBase.addUser(user);

        res.setStatus(HttpStatus.FOUND);
        res.addHeader(LOCATION_HEADER_KEY, "/index.html");
    }

    @Override
    public String getPath() {
        return USER_CREATE_URL;
    }
}
