package web.http;

import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    private static final String EQUAL_SIGN = "=";

    private Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie from(String cookie) {
        String[] values = cookie.split(EQUAL_SIGN);
        Map<String, String> cookies = new HashMap<>();
        cookies.put(values[0], values[1]);
        return new HttpCookie(cookies);
    }

    public String getCookie(String key) {
        return this.cookies.get(key);
    }
}
