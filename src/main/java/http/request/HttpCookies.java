package http.request;

import http.common.HttpCookie;

import java.util.HashMap;
import java.util.Map;

public class HttpCookies {
    private final Map<String, HttpCookie> httpCookies;

    public HttpCookies() {
        this(new HashMap<>());
    }

    public HttpCookies(Map<String, HttpCookie> httpCookies) {
        this.httpCookies = httpCookies;
    }

    public HttpCookie get(String cookieName) {
        return httpCookies.get(cookieName);
    }

    public void put(String cookieName, HttpCookie httpCookie) {
        httpCookies.put(cookieName, httpCookie);
    }
}
