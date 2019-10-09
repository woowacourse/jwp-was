package http.response;

import http.Cookie;
import http.request.exception.NotFoundHttpRequestHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HttpResponseHeader {
    private Map<String, String> headers = new HashMap<>();
    private Cookie cookie;

    HttpResponseHeader(Cookie cookie) {
        this.cookie = cookie;
    }

    void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addCookie(String name, String value) {
        cookie.addCookie(name, value);
    }

    boolean hasCookie() {
        return !cookie.isEmpty();
    }

    String getHeader(String key) {
        return Optional.ofNullable(headers.get(key))
                .orElseThrow(() -> new NotFoundHttpRequestHeader(key));
    }

    Set<String> getKeySet() {
        return headers.keySet();
    }

    public String getCookieValues() {
        return cookie.build();
    }
}
