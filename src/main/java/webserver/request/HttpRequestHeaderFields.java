package webserver.request;

import webserver.Cookie;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeaderFields {

    private final Map<String, String> fields = new HashMap<>();
    private final Cookie cookie = new Cookie();

    public void addField(String name, String value) {
        fields.put(name, value);
    }

    public String findField(final String name) {
        return fields.get(name);
    }

    public void addCookieField(String name, String value) {
        cookie.put(name, value);
    }

    public String findCookieField(String name) {
        return cookie.get(name);
    }
}
