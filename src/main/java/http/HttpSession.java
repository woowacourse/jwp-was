package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private final String id;
    private final Map<String, Object> attribute;

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
        this.attribute = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public Object getAttribute(final String name) {
        return this.attribute.get(name);
    }

    public void setAttribute(final String name, Object value) {
        this.attribute.put(name, value);
    }

    public void removeAttribute(final String name) {
        this.attribute.remove(name);
    }

    public void invalidate() {
        this.attribute.clear();
    }
}
