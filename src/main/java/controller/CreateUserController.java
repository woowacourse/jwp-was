package controller;

import db.DataBase;
import http.request.Request;
import http.response.Response;
import http.support.HttpStatus;
import model.User;

public class CreateUserController extends AbstractController {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String INDEX_HTML = "/index.html";

    @Override
    public void doPost(Request request, Response response) {
        saveUser(request);
        response.setHttpStatus(HttpStatus.FOUND);
        response.sendRedirect(INDEX_HTML, HttpStatus.FOUND);
    }

    private void saveUser(Request request) {
        DataBase.addUser(getUser(request));
    }

    private User getUser(Request request) {
        return new User(request.getParameter(USER_ID), request.getParameter(PASSWORD), request.getParameter(NAME), request.getParameter(EMAIL));
    }
}
