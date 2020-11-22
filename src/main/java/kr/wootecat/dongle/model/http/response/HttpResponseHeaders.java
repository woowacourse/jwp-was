package kr.wootecat.dongle.model.http.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import kr.wootecat.dongle.model.http.Cookie;
import kr.wootecat.dongle.model.http.Cookies;

public class HttpResponseHeaders {

    private final Map<String, String> responseHeaders;
    private final Cookies setCookies;

    private HttpResponseHeaders(Map<String, String> responseHeaders, Cookies setCookies) {
        this.responseHeaders = responseHeaders;
        this.setCookies = setCookies;
    }

    public static HttpResponseHeaders ofEmpty() {
        return new HttpResponseHeaders(new LinkedHashMap<>(), new Cookies(new ArrayList<>()));
    }

    public Map<String, String> getResponseHeaders() {
        return Collections.unmodifiableMap(responseHeaders);
    }

    public void addHeader(String name, String value) {
        responseHeaders.put(name, value);
    }

    public void addCookie(Cookie cookie) {
        setCookies.add(cookie);
    }

    public Collection<Cookie> getCookies() {
        return setCookies.getCookies();
    }

    public Cookie getCookie(String name) {
        return setCookies.get(name);
    }
}
