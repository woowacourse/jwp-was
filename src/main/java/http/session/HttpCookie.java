package http.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpCookie {

    private Map<String, String> cookies;

    public HttpCookie() {
        cookies = new HashMap<>();
    }

    public HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public Map<String, String> getCookies() {
        return Collections.unmodifiableMap(cookies);
    }
}
