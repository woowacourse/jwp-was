package http.common;

import http.parser.CookiesParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cookies {

    private final Map<String, String> cookies;

    public Cookies() {
        cookies = new HashMap<>();
    }

    public Cookies(String line) {
        this.cookies = CookiesParser.parse(line);
    }

    public String getCookie(final String name) {
        return cookies.get(name);
    }

    public void addCookie(final Cookie cookie) {
        cookies.put(cookie.getName(), cookie.getValue());
    }

    public boolean hasCookie() {
        return !cookies.isEmpty();
    }

    public Set<Entry<String,String>> toEntrySet() {
        return cookies.entrySet();
    }
}
