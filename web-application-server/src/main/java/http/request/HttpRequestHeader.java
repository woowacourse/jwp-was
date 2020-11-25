package http.request;

import java.util.Map;

import http.Cookies;

public class HttpRequestHeader {

    private static final String COOKIE = "Cookie";

    private final Map<String, String> requestHeader;
    private final Cookies cookies;

    public HttpRequestHeader(final Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
        this.cookies = Cookies.from(requestHeader.get(COOKIE));
    }

    public String getValue(final String name) {
        return this.requestHeader.get(name);
    }

    public Cookies getCookies() {
        return cookies;
    }
}
