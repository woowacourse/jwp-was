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

    String getId() {
        return sessionId;
    }

    void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    Object getAttribute(String name) {
        return session.get(name);
    }

    void removeAttribute(String name) {
        session.remove(name);
    }

    void invalidate() {
        session.clear();
    }
}
