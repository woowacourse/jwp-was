package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private final String sessionId;
    private final Map<String, Object> attributes;

    public HttpSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.attributes = new HashMap<>();
    }

    public String getId() {
        return this.sessionId;
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
    }
}
