package http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private String id;
    private Map<String, Object> attributes;

    public HttpSession(String id) {
        this.id = id;
        attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
}
