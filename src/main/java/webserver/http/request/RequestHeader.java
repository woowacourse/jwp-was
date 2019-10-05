package webserver.http.request;

import webserver.http.Cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> headers = new HashMap<>();

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public String getHeaderValue(String header) {
        return headers.get(header);
    }

    public int getContentLength() {
        return Integer.valueOf(headers.get("Content-Length"));
    }

    // TODO : refactor
    public List<Cookie> getCookies() {
        String cookieString = headers.get("Cookie");
        if (cookieString != null) {
            List<Cookie> cookies = new ArrayList<>();

            for (String cookie : cookieString.split("; ")) {
                String[] splicedCookie = cookie.split("=");
                cookies.add(new Cookie(splicedCookie[0], splicedCookie[1]));
            }
            return cookies;
        }
        return null;
    }

    public boolean isCookieExist() {
        return headers.get("Cookie") != null;
    }
}
