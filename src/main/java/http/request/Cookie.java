package http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cookie {
    private Map<String, String> cookies = new HashMap<>();

    public Cookie() {
    }

    public Cookie(String rawCookie) {
        if (rawCookie.length() != 0) {
            String[] tokens = rawCookie.split("; ");
            for (String token : tokens) {
                String[] tmp = token.split("=");
                this.cookies.put(tmp[0], tmp[1]);
            }
        }
    }

    public boolean hasCookie(String key) {
        return this.cookies.containsKey(key);
    }

    public String getCookie(String key) {
        return this.cookies.get(key);
    }

    public Set<Map.Entry<String, String>> getAllCookie() {
        return this.cookies.entrySet();
    }

    public void setCookie(String key, String value) {
        this.cookies.put(key, value);
    }
}
