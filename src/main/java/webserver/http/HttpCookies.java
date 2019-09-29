package webserver.http;

import webserver.http.headerfields.HttpCookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HttpCookies {
    private static final String SET_COOKIE = "Set-Cookie";

    private List<HttpCookie> cookies;

    public HttpCookies() {
        this.cookies = new ArrayList<>();
    }

    public void add(HttpCookie cookie) {
        cookies.add(cookie);
    }

    public Optional<String> responseString() {
        StringBuilder result = new StringBuilder();

        cookies.forEach(cookie ->
                result.append(SET_COOKIE)
                        .append(": ")
                        .append(cookie.line())
                        .append("\r\n")
        );
        return Optional.ofNullable(result.toString());
    }
}
