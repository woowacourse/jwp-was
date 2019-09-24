package webserver.http;

import webserver.http.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cookie {
    private final Map<String, String> map;

    public Cookie() {
        this.map = new HashMap<>();
    }

    public Cookie(final String cookie) {
        this.map = HttpUtils.parseCookie(cookie);
    }

    public String get(final String key) {
        return map.get(key);
    }

    public void put(final String key, final String value) {
        map.put(key, value);
    }

    public int size() {
        return map.size();
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }
}
