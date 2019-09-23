package http.model.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpCookie {
    private Map<String, String> cookies;

    public HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public HttpCookie() {
        cookies = new HashMap<>();
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public Set<String> getKeySet() {
        return Collections.unmodifiableSet(cookies.keySet());
    }

    public Map<String, String> getCookies() {
        return Collections.unmodifiableMap(cookies);
    }
}
