package webserver.http.cookie;

import java.util.HashMap;
import java.util.Map;

public class Cookies {
    private Map<String, Cookie> cookies = new HashMap<>();

    public void addCookie(String key, String value) {
        cookies.put(key, new Cookie(key, value));
    }

    public Map<String, Cookie> getCookies() {
        return cookies;
    }

    public String getCookieBy(String key) {
        Cookie cookie = cookies.get(key);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }
}
