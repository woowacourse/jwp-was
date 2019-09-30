package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String id;
    private Map<String, Object> attributes = new HashMap<>();

    public HttpSession() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void invalidate() {
        attributes.clear();
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttributes(String name, Object value) {
        attributes.put(name, value);
    }
}
