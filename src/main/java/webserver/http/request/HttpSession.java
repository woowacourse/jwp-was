package webserver.http.request;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {
    private final String id;
    private final Map<String, Object> attribute;

    HttpSession(String id, Map<String, Object> attribute) {
        this.id = id;
        this.attribute = attribute;
    }

    public static HttpSession create() {
        return new HttpSession(UUID.randomUUID().toString(), new ConcurrentHashMap<>());
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.clear();
    }
}
