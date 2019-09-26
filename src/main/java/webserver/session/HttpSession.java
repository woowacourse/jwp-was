package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String id;
    private Map<String, Object> session;

    protected HttpSession(String id) {
        this.id = id;
        this.session = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(String name) {
        return session.get(name);
    }

    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public void removeAttribute(String name) {
        session.remove(name);
    }

    public void invalidate() {
        session = new HashMap<>();
    }
}
