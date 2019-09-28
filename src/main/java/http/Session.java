package http;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private String id;
    private Map<String, Object> attribute;

    public Session(String id) {
        attribute = new HashMap<>();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
    }
}
