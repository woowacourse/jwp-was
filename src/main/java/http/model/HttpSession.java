package http.model;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private Map<String, Object> session;
    private String id;

    public HttpSession(String id) {
        session = new HashMap<>();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttributes(String name, Object value) {
        session.put(name, value);
    }

    public Object getAttribute(String name) {
        return session.get(name);
    }

    public void removeAttribute(String name) {
        session.remove(name);
    }

    public void invalidate() {
        session.clear();
    }
}
