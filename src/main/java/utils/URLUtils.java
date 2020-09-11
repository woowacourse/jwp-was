package utils;

import model.User;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class URLUtils {
    public static final String REQUEST_LINE_DELIMITER = " ";
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String URL_PARAMETER_DELIMITER = "\\?";
    public static final int URL_INDEX = 1;
    public static final int ZERO = 0;
    public static final int ONE = 1;

    public static String parseURL(String requestLine) {
        final String requestURL = parseRequestURL(requestLine);
        return requestURL.split(URL_PARAMETER_DELIMITER)[ZERO];
    }

    public static User parseUser(String requestLine) {
        final String requestURL = parseRequestURL(requestLine);
        final String parameterString = requestURL.split(URL_PARAMETER_DELIMITER)[ONE];
        final String[] pairs = parameterString.split(PARAMETER_DELIMITER);

        final Map<String, String> parameters = Arrays.stream(pairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[ZERO], it -> it[ONE]));

        return new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
    }

    private static String parseRequestURL(String requestLine) {
        return requestLine.split(REQUEST_LINE_DELIMITER)[URL_INDEX];
    }
}
