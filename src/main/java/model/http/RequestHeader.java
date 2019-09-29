package model.http;

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

    // TODO : 리팩토링 필요
    public List<Cookie> getCookies() {
        String cookieString = headers.get("Cookie");
        if (cookieString != null) {
            String[] splicedCookieString = cookieString.split("; ");
            List<Cookie> cookies = new ArrayList<>();

            for (String cookie : splicedCookieString) {
                String[] splicedCookie = cookie.split("=");
                cookies.add(new Cookie(splicedCookie[0], splicedCookie[1]));
            }
            return cookies;
        }
        return null;
    }
}
