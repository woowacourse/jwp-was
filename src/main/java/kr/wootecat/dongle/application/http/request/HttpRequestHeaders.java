package kr.wootecat.dongle.application.http.request;

import java.util.List;
import java.util.Map;

import kr.wootecat.dongle.application.http.Cookie;

class HttpRequestHeaders {

    private final Map<String, String> headers;
    private final List<Cookie> cookies;

    public HttpRequestHeaders(Map<String, String> headers, List<Cookie> cookies) {
        this.headers = headers;
        this.cookies = cookies;
    }

    public boolean containsCookie(String name, boolean value) {
        return containsCookie(name, String.valueOf(value));
    }

    public boolean containsCookie(String name, String value) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.hasValue(name, value));
    }

    public String get(String key) {
        return headers.get(key);
    }
}
