package utils;

import static java.util.Collections.*;
import static kr.wootecat.dongle.application.http.Cookie.*;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Cookie> toCookie(String key, String value) {
        List<Cookie> cookies = new ArrayList<>();
        String[] keyValue = value.split(DELIMITER);
        for (String cookiePair : keyValue) {
            String[] split = cookiePair.split(KEY_VALUE_DELIMITER);
            String cookieName = split[0];
            String cookieValue = split.length == 2 ? split[1] : "";
            cookies.add(new Cookie(cookieName, cookieValue));
        }
        return unmodifiableList(cookies);
    }
}
