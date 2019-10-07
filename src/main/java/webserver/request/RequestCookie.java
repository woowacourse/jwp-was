package webserver.request;

import java.util.Map;

public class RequestCookie {
    private Map<String, String> cookie;

    public RequestCookie(Map<String, String> cookie) {
        this.cookie = cookie;
    }

    public String getCookie(String key) {
        return cookie.get(key);
    }
}
