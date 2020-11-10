package webserver;

import java.util.ArrayList;
import java.util.List;

public class CookieUtils {

    public static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private static final String COOKIE_HEADER_DELIMITER = "; ";

    public static List<Cookie> parse(String header) {
        List<Cookie> cookies = new ArrayList<>();
        String[] cookiesSegments = header.split(COOKIE_HEADER_DELIMITER);
        for (String cookie : cookiesSegments) {
            final int i = cookie.indexOf(COOKIE_KEY_VALUE_DELIMITER);
            final String key = cookie.substring(0, i);
            final String value = cookie.substring(i + 1);

            cookies.add(new Cookie(key, value));
        }
        return cookies;
    }
}
