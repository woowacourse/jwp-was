package kr.wootecat.dongle.http.request;

import java.util.List;
import java.util.Map;

import kr.wootecat.dongle.http.Cookie;

class HttpRequestHeaders {

    private final Map<String, String> headers;
    private final List<Cookie> cookies;

    public HttpRequestHeaders(Map<String, String> headers, List<Cookie> cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    public boolean hasCookieWithPair(String name, boolean value) {
        return hasCookieWithPair(name, String.valueOf(value));
    }

    public boolean hasCookieWithPair(String name, String value) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.hasValue(name, value));
    }

    public boolean containsCookie(String name) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.hasName(name));
    }

    public String get(String key) {
        return headers.get(key);
    }
}
