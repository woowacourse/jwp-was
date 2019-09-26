package dev.luffy.http.request;

import java.util.HashMap;
import java.util.Map;

import dev.luffy.http.excption.NotFoundCookieException;

public class HttpRequestCookie {

    private Map<String, String> cookie;

    public HttpRequestCookie() {
        cookie = new HashMap<>();
    }

    public void put(String key, String value) {
        cookie.put(key, value);
    }

    public String get(String key) {
        if (!cookie.containsKey(key)) {
            throw new NotFoundCookieException("그런 쿠키가 없어요.");
        }
        return cookie.get(key);
    }

    @Override
    public String toString() {
        return "HttpRequestCookie{" +
                "cookie=" + cookie +
                '}';
    }

    public void addCookies(Map<String, String> cookie) {
        cookie.forEach(this.cookie::put);
    }
}
