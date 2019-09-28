package webserver.http;

import webserver.http.headerfields.HttpCookie;

import java.util.ArrayList;
import java.util.List;

public class HttpCookies {
    private List<HttpCookie> cookies;

    public HttpCookies() {
        this.cookies = new ArrayList<>();
    }

    public void add(HttpCookie cookie) {
        cookies.add(cookie);
    }

    public List<HttpCookie> cookies() {
        return cookies;
    }
}
