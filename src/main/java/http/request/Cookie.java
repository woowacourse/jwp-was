package http.request;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    public static final String NAME = "Cookie";
    public static final String DELIMITER_OF_ELEMENT = ";";
    public static final String DELIMITER_OF_KEY_VALUE = "=";

    private final Map<String, String> cookies;

    public Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Cookie(String cookieLine) {
        Map<String, String> cookies = new HashMap<>();
        if (cookieLine == null) {
            this.cookies = cookies;
            return;
        }
        for (String cookie : cookieLine.split(DELIMITER_OF_ELEMENT)) {
            String trimedCookie = cookie.trim();
            cookies.put(trimedCookie.split(DELIMITER_OF_KEY_VALUE)[0], trimedCookie.split(DELIMITER_OF_KEY_VALUE)[1]);
        }
        this.cookies = cookies;
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public boolean containsKey(String key) {
        return cookies.containsKey(key);
    }
}
