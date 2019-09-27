package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    private String sessionId;
    private Map<String, Object> session;

    private Session(String sessionId) {
        this.sessionId = sessionId;
        this.session = new HashMap<>();
    }

    public static Session of() {
        return new Session(UUID.randomUUID().toString());
    }

    public String getId() {
        return sessionId;
    }

    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public Object getAttribute(String name) {
        return session.get(name);
    }

    public void removeAttribute(String name) {
        session.remove(name);
    }

    public void invalidate() {
        session.clear();
    }
}
