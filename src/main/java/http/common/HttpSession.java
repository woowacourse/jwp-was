package http.common;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    public static final String SESSION_NAME = "JSESSIONID";

    private final String uuid;
    private final Map<String, Object> session;

    HttpSession(String uuid) {
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

    public String getUuid() {
        return uuid;
    }

    public void invalidate() {
        HttpSessionHandler.removeSession(uuid);
    }
}