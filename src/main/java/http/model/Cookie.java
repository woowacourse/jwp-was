package http.model;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    Map<String, String> cookieMap;

    public Cookie(String cookies) {
        if (cookies == null) {
            return;
        }
        String[] eachCookies = cookies.split("; ");

        Map<String, String> cookieMap = new HashMap<>();
        for (String eachCookie : eachCookies) {
            String[] cookiePair = eachCookie.split("=");
            cookieMap.put(cookiePair[0], cookiePair[1]);
        }
        this.cookieMap = cookieMap;
    }

    public boolean hasCookie(String key) {
        return cookieMap != null && cookieMap.get(key) != null;
    }
}
