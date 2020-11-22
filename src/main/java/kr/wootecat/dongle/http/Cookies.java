package kr.wootecat.dongle.http;

import java.util.List;

public class Cookies {
    private final List<Cookie> cookies;

    public Cookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public boolean hasCookieWithPair(String name, String value) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.hasValue(name, value));
    }

    public boolean containsCookie(String name) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.hasName(name));
    }

    public String get(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.hasName(name))
                .findAny()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
