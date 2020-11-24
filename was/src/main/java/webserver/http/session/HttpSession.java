package webserver.http.session;

import java.util.Map;

public class HttpSession {
    private final String id;
    private final Map<String, Object> attributes;

    public HttpSession(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
    }

    public String getId() {
        return this.id;
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }
}
