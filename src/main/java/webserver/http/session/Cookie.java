package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private Map<String, Object> cookie = new HashMap<>();

    public void add(String key, Object value) {
        cookie.put(key, value);
    }

    public Object get(String key) {
        return cookie.get(key);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : cookie.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }
}
