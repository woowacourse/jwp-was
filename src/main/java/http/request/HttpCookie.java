package http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    private static final String COOKIE_DELIMITER = "; ";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie of(String cookie) {
        String[] tokens = cookie.split(COOKIE_DELIMITER);
        Map<String, String> cookies = new HashMap<>();

        if (!"".equals(cookie)) {
            Arrays.stream(tokens)
                    .forEach(s ->
                            cookies.put(s.split(COOKIE_KEY_VALUE_DELIMITER)[0],
                                    s.split(COOKIE_KEY_VALUE_DELIMITER)[1]));
            return new HttpCookie(cookies);
        }
        return new HttpCookie(Collections.emptyMap());
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public boolean contains(String key) {
        return cookies.containsKey(key);
    }
}
