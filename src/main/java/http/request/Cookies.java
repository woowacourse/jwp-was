package http.request;

import java.util.HashMap;
import java.util.Map;

public class Cookies {
    private static final String COOKIES_DELIMITER = "; ";
    private static final String COOKIE_DELIMITER = "=";

    private final Map<String, String> cookies = new HashMap<>();

    public Cookies(String cookies) {
        if (cookies == null) {
            return;
        }
        String[] tokens = cookies.split(COOKIES_DELIMITER);
        for (String cookie : tokens) {
            String[] value = cookie.split(COOKIE_DELIMITER);
            this.cookies.put(value[0], value[1]);
        }
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }
}
