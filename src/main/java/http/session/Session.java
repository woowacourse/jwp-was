package http.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<String, Object> attributes = new HashMap<>();
    private String sessionId;

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttriubte(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void invalidate() {
        attributes = new HashMap<>();
    }
}
