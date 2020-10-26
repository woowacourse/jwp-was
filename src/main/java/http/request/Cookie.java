package http.request;

import java.util.HashMap;
import java.util.Map;

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

    public void setCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("; ");
        }
        return stringBuilder.toString();
    }
}
