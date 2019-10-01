package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final Map<String, Object> sessionValues = new HashMap<>();

    private final String uuid;

    HttpSession(final String uuid) {
        this.uuid = uuid;
    }

    public void setAttribute(final String key, final Object value) {
        sessionValues.put(key, value);
    }

    public Object getAttribute(final String key) {
        return sessionValues.get(key);
    }

    public void removeAttribute(final String key) {
        sessionValues.remove(key);
    }

    public void invalidate() {
        sessionValues.clear();
        SessionManager.remove(uuid);
    }

    public String getId() {
        return uuid;
    }
}
