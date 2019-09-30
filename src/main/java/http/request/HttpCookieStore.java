package http.request;

import http.HttpCookie;
import http.exception.NotFoundCookieException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpCookieStore {
    private List<HttpCookie> cookies;

    public HttpCookieStore(List<HttpCookie> cookies) {
        if (cookies.isEmpty()) {
            this.cookies = new ArrayList<>();
            return;
        }

        this.cookies = cookies;
    }

    public void addCookie(HttpCookie httpCookie) {
        cookies.add(httpCookie);
    }

    public String getCookieValue(String key) {
        return getCookie(key).getValue();
    }

    public boolean containsValue(String key) {
        return cookies.stream()
                .anyMatch(cookie -> cookie.hasValueByKey(key));
    }

    public HttpCookie getCookie(String key) {
        return cookies.stream()
                .filter(httpCookie -> httpCookie.hasValueByKey(key))
                .findAny()
                .orElseThrow(NotFoundCookieException::new);
    }

    @Override
    public String toString() {
        return "Cookie: " +
                cookies.stream()
                        .map(HttpCookie::toString)
                        .collect(Collectors.joining(";"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpCookieStore that = (HttpCookieStore) o;
        return cookies.equals(that.cookies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookies);
    }
}
