package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private String id;
    private Map<String, Object> attributes;

    public HttpSession(String id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }
}