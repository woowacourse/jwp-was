package server.web;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    public static final String JSESSIONID = "JSESSIONID";

    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
    }
}
