package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final UUID uuid;
    private Map<String, Object> attributes;

    public HttpSession(UUID uuid) {
        this.uuid = uuid;
        attributes = new HashMap<>();
    }

    String getId() {
        return uuid.toString();
    }

    public void setAttributes(String name, Object value) {
        attributes.put(name, value);
    }

    public String getSessionId() {
        return uuid.toString();
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes = new HashMap<>();
    }

    public boolean checkAttribute(String name, Object value) {
        Object o = attributes.get(name);
        if (o == null) {
            return false;
        }
        return o.equals(value);
    }
}
