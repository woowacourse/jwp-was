package web.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HttpSession implements Session {
    public static final String SESSION_ID = "JSESSIONID";

    private final String id;
    private final ConcurrentMap<String, Object> attributes;

    public HttpSession(String id) {
        this.id = id;
        this.attributes = new ConcurrentHashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        SessionStorage.remove(id);
    }
}
