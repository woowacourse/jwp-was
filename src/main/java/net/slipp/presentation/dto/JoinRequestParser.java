package net.slipp.presentation.dto;

import webserver.servlet.http.request.HttpRequest;

public class JoinRequestParser {

    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_EMAIL = "email";

    public static JoinRequest parseRequest(HttpRequest request) {
        String userId = request.getParameter(PARAM_USER_ID);
        String password = request.getParameter(PARAM_PASSWORD);
        String name = request.getParameter(PARAM_NAME);
        String email = request.getParameter(PARAM_EMAIL);

        return new JoinRequest(userId, password, name, email);
    }
}
