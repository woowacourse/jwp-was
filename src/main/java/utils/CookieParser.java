package utils;

import static kr.wootecat.dongle.application.http.Cookie.*;

import kr.wootecat.dongle.application.http.Cookie;

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

        return result;
    }

    public static Cookie toCookie(String headerValue) {
        return null;
    }
}
