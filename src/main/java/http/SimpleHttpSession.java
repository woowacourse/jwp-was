package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleHttpSession implements HttpSession {
    private static Map<String, HttpSession> HTTP_SESSION_STORAGE = new HashMap<>();

    private String id;
    private Map<String, Object> attributes;

    public static HttpSession getHttpSessionStorage(String sessionId) {
        return HTTP_SESSION_STORAGE.getOrDefault(sessionId, new SimpleHttpSession());
    }

    private SimpleHttpSession() {
        this.id = UUID.randomUUID().toString();
        this.attributes = new HashMap<>();
        HTTP_SESSION_STORAGE.put(id, this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }
}
