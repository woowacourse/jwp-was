package jwp.was.webapplicationserver.configure.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cookies {

    private static final String COOKIE_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final Cookies EMPTY = new Cookies(new HashMap<>());
    private static final int COOKIE_STANDARD_LENGTH = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> cookies;

    public Cookies(Map<String, String> cookie) {
        this.cookies = cookie;
    }

    public static Cookies from(String cookie) {
        if (Objects.isNull(cookie) || cookie.isEmpty()) {
            return EMPTY;
        }
        return new Cookies(parseCookie(cookie));
    }

    private static Map<String, String> parseCookie(String cookie) {
        Map<String, String> cookies = new HashMap<>();
        for (String cookieAttribute : cookie.split(COOKIE_DELIMITER)) {
            String[] split = cookieAttribute.split(KEY_VALUE_DELIMITER);
            putCookie(cookies, split);
        }
        return cookies;
    }

    private static void putCookie(Map<String, String> cookies, String[] split) {
        if (split.length != COOKIE_STANDARD_LENGTH) {
            return;
        }
        cookies.put(split[KEY_INDEX], split[VALUE_INDEX]);
    }

    public String get(String key) {
        return cookies.get(key);
    }
}
