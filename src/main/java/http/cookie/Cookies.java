package http.cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.net.HttpHeaders.SET_COOKIE;
import static http.common.HttpHeader.HEADER_FIELD_FORMAT;

public class Cookies {
    private final List<Cookie> cookies = new ArrayList<>();

    public Cookies() {
    }

    public Cookies(List<Cookie> cookies) {
        if (cookies != null) {
            this.cookies.addAll(cookies);
        }
    }

    public void add(Cookie cookie) {
        if (cookie != null) {
            cookies.add(cookie);
        }
    }

    public void addAll(Cookies cookies) {
        if (!cookies.isEmpty()) {
            this.cookies.addAll(cookies.cookies);
        }
    }

    private boolean isEmpty() {
        return this.cookies.isEmpty();
    }

    public Cookie getCookie(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.matchName(name))
                .findAny()
                .orElse(Cookie.EMPTY);
    }

    public String serialize() {
        return cookies.stream()
                .map(Cookie::serialize)
                .map(cookie -> String.format(HEADER_FIELD_FORMAT, SET_COOKIE, cookie))
                .collect(Collectors.joining());
    }
}
