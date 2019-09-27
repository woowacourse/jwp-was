package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    public static final String SESSION_NAME = "JSESSIONID";

    private final UUID uuid;
    private final Map<String, Object> session;

    HttpSession(UUID uuid) {
        this.uuid = uuid;
        this.session = new HashMap<>();
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

    public UUID getUuid() {
        return uuid;
    }

    public void invalidate() {
        HttpSessionHandler.removeSession(uuid);
    }
}