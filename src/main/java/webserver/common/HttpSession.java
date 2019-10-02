package webserver.common;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private Map<String, Object> values;
    private final String id;

    private HttpSession(String id) {
        this.values = new HashMap<>();
        this.id = id;
    }

    public static HttpSession of(String id) {
        return new HttpSession(id);
    }

    public void setAttribute(String name, Object value) {
        this.values.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.values.get(name);
    }

    public void removeAttribute(String name) {
        this.values.remove(name);
    }

    public void invalidate() {
        this.values.clear();
    }

    public String getId() {
        return id;
    }
}
