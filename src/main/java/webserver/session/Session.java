package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    private final Map<String, Object> sessionValues = new HashMap<>();

    private final String uuid;

    Session() {
        uuid = UUID.randomUUID().toString();
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
    }

    public String getId() {
        return uuid;
    }
}
