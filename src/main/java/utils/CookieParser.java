package utils;

import static webserver.servlet.http.Cookie.*;

import webserver.servlet.http.Cookie;

public class CookieParser {
    private static final String DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";

    private CookieParser() {
    }

    public static String parse(Cookie cookie) {
        String name = cookie.getName();
        String value = cookie.getValue();

        String result = name + KEY_VALUE_DELIMITER + value;
        if (cookie.hasPath()) {
            result += DELIMITER;
            result += PATH + KEY_VALUE_DELIMITER + cookie.getPath();
        }

        if (cookie.hasMaxAge()) {
            result += DELIMITER;
            result += MAX_AGE + KEY_VALUE_DELIMITER + cookie.getMaxAge();
        }

        return result;
    }
}
