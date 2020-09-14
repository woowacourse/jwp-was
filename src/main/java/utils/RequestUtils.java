package utils;

import model.User;

public class RequestUtils {
    public static final String REGEX = " ";
    public static final int URL_INDEX = 1;
    public static final int REQUEST_TYPE_INDEX = 0;

    public static boolean isPost(String header) {
        String[] method = header.split(REGEX);
        return "POST".equals(method[REQUEST_TYPE_INDEX]);
    }

    public static String extractUrl(String header) {
        String[] method = header.split(REGEX);
        return method[URL_INDEX];
    }

    public static User extractUser(String body) {
        String[] parameters = body.split("&");
        String userId = getParameter(parameters[0]);
        String password = getParameter(parameters[1]);
        String name = getParameter(parameters[2]);
        String email = getParameter(parameters[3]);
        return new User(userId, password, name, email);
    }

    private static String getParameter(String expression) {
        return expression.split("=")[1];
    }
}
