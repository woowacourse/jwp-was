package utils;

import model.User;

public class UserExtractor implements ModelObjectExtractor {
    @Override
    public User extract(String body) {
        String[] parameters = body.split("&");
        String userId = getParameter(parameters[0]);
        String password = getParameter(parameters[1]);
        String name = getParameter(parameters[2]);
        String email = getParameter(parameters[3]);
        return new User(userId, password, name, email);
    }

    private String getParameter(String expression) {
        return expression.split("=")[1];
    }
}
