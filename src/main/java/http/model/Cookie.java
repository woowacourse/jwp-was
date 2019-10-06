package http.model;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    Map<String, String> cookieMap;

    public Cookie(String cookies) {
        cookieMap = new HashMap<>();

        if (cookies == null) {
            return;
        }
        String[] eachCookies = cookies.split("; ");

        for (String eachCookie : eachCookies) {
            String[] cookiePair = eachCookie.split("=");
            cookieMap.put(cookiePair[0], cookiePair[1]);
        }
    }

    public String getCookieValue(String key) {
        return cookieMap.get(key);
    }
}
