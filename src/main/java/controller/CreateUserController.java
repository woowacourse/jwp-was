package controller;

import db.DataBase;
import model.HttpRequest;
import model.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {
    private static final String ORIGIN = "Origin";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String INDEX_HTML = "/index.html";

    @Override
    public void doPost(HttpRequest request, HttpResponse httpResponse) {
        saveUser(request);
        httpResponse.response300(request.getHeader(ORIGIN) + INDEX_HTML);
    }

    private void saveUser(HttpRequest request) {
        User user = new User(request.getParameter(USER_ID), request.getParameter(PASSWORD), request.getParameter(NAME), request.getParameter(EMAIL));
        DataBase.addUser(user);
    }
}
