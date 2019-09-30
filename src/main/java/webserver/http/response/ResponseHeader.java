package webserver.http.response;

import webserver.http.Cookie;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private Map<String, String> headers = new HashMap<>();

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addCookie(Cookie cookie) {
        headers.put(cookie.getHeaderKey(), cookie.getHeaderValues());
    }

    @Override
    public String toString() {
        String responseHeaders = "";
        for (String key : headers.keySet()) {
            responseHeaders += (key + headers.get(key) + "\r\n");
        }
        return responseHeaders;
    }
}
