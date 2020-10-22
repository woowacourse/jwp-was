package session.model;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

public class HttpSession {
    private final String id;
    private final Map<String, Object> attributes;

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
        this.attributes = Maps.newHashMap();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
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

    public String getId() {
        return id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
