package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String uuid;
    private Map<String, Object> attributes;

    public HttpSession(String uuid) {
        this.uuid = uuid;
        attributes = new HashMap<>();
    }

    String getId() {
        return uuid;
    }

    public void setAttributes(String name, Object value) {
        attributes.put(name, value);
    }

    public String getSessionId() {
        return uuid;
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
