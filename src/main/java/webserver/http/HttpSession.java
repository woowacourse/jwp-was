package webserver.http;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {
    private final Map<String, Object> map = new ConcurrentHashMap<>();

    private final String id;

    public HttpSession() {
        id = UUID.randomUUID().toString();
    }

    public void setAttribute(final String name, final Object value) {
        map.put(name, value);
    }

    public Object getAttribute(final String name) {
        return map.get(name);
    }

    public void invalidate() {
        map.clear();
    }

    public int size() {
        return map.size();
    }

    public void removeAttribute(final String name) {
        map.remove(name);
    }

    public String getId() {
        return id;
    }
}

