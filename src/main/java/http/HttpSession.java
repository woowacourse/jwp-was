package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String id;
    private Map<String, Object> attributes = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void removeAttribute(String key) {
        if (getAttribute(key) == null) {
            return;
        }

        attributes.remove(key);
    }

    public void invalidate() {
        attributes.clear();
    }
}
