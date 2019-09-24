package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private final String id;
    private final Map<String, Object> attributes;
    private boolean isValid;

    private HttpSession(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
        isValid = true;
    }

    public static HttpSession create() {
        return new HttpSession(UUID.randomUUID().toString(), new HashMap<>());
    }

    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
        isValid = false;
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public boolean isValid() {
        return isValid;
    }
}
