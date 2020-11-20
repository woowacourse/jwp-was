package service;

import db.DataBase;
import http.request.HttpRequest;
import model.User;

public class UserService {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    public void createRequestParams(final HttpRequest httpRequest) {
        User user = new User(httpRequest.getHttpRequestParamsByName(USER_ID), httpRequest.getHttpRequestParamsByName(PASSWORD), httpRequest.getHttpRequestParamsByName(NAME), httpRequest
                .getHttpRequestParamsByName(EMAIL));
        DataBase.addUser(user);
    }

    public void createRequestBody(final HttpRequest httpRequest) {
        User user = new User(httpRequest.getHttpRequestBodyByName(USER_ID), httpRequest.getHttpRequestBodyByName(PASSWORD), httpRequest.getHttpRequestBodyByName(NAME), httpRequest
                .getHttpRequestBodyByName(EMAIL));
        DataBase.addUser(user);
    }

    public User login(final String userId) {
        return DataBase.findUserById(userId);
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }
}
