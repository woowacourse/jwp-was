package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private final String id;
    private Map<String, Object> objects = new HashMap<>();

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        objects.put(name, value);
    }

    public Object getAttribute(String name) {
        return objects.get(name);
    }

    public void removeAttribute(String name) {
        objects.remove(name);
    }

    public void invalidate() {
        objects.clear();
    }
}
