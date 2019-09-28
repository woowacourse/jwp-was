package session;

import com.google.common.collect.Maps;

import java.util.Map;

public class Session {
    private Map<String, Object> attributes = Maps.newHashMap();
    private final String sessionId;

    public Session(String sessionId) {
        this.sessionId = sessionId;
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

    public String getId() {
        return sessionId;
    }

    public void invalidate() {
        attributes = Maps.newHashMap();
    }
}