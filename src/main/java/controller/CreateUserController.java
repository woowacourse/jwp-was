package controller;

import db.DataBase;
import model.Request;
import model.Response;
import model.User;

public class CreateUserController extends AbstractController {
    private static final String ORIGIN = "Origin";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String INDEX_HTML = "/index.html";

    @Override
    void doPost(Request request, Response response) {
        saveUser(request);
        response.response300(request.getHeader(ORIGIN) + INDEX_HTML);
    }

    @Override
    void doGet(Request request, Response response) {
    }

    private void saveUser(Request request) {
        User user = new User(request.getParameter(USER_ID), request.getParameter(PASSWORD), request.getParameter(NAME), request.getParameter(EMAIL));
        DataBase.addUser(user);
    }
}
