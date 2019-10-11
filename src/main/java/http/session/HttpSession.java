package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String id;
    private final Map<String, Object> attributes;

    public HttpSession(String id) {
        this.id = id;
        attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
