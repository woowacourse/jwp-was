package utils;

import model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUtils {
    public static final String REQUEST_LINE_DELIMITER = " ";
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String URL_PARAMETER_DELIMITER = "\\?";
    public static final int INDEX_ZERO = 0;
    public static final int INDEX_ONE = 1;

    public static String parseMethod(String requestLine) {
        return requestLine.split(REQUEST_LINE_DELIMITER)[INDEX_ZERO];
    }

    private static String parseRequestURL(String requestLine) {
        return requestLine.split(REQUEST_LINE_DELIMITER)[INDEX_ONE];
    }

    public static String parseURL(String requestLine) {
        final String requestURL = parseRequestURL(requestLine);
        return requestURL.split(URL_PARAMETER_DELIMITER)[INDEX_ZERO];
    }

    public static User parseUser(String requestLine) {
        final String requestURL = parseRequestURL(requestLine);
        final String parameterString = requestURL.split(URL_PARAMETER_DELIMITER)[INDEX_ONE];
        final String[] pairs = parameterString.split(PARAMETER_DELIMITER);

        final Map<String, String> parameters = Arrays.stream(pairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[INDEX_ZERO], it -> it[INDEX_ONE]));

        return new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
    }

    public static User parseBody(String requestBody) {
        final String[] pairs = requestBody.split(PARAMETER_DELIMITER);

        final Map<String, String> parameters = Arrays.stream(pairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[INDEX_ZERO], it -> it[INDEX_ONE]));

        return new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
    }

    public static Map<String, String> parseHeaders(List<String> headers) {
        return headers.stream()
                .map(header -> header.split(": "))
                .collect(Collectors.toMap(it -> it[INDEX_ZERO], it -> it[INDEX_ONE]));
    }
}
