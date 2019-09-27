package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String id;
    private final Map<String, Object> session;

    public HttpSession(final String id) {
        this.id = id;
        this.session = new HashMap<>();
    }

    public void setAttribute(final String key, final String value) {
        session.put(key, value);
    }

    public Object getAttribute(final String key) {
        return session.get(key);
    }

    public void removeAttribute(final String key) {
        session.remove(key);
    }

    public void invalidate() {
        session.clear();
    }

    public String getId() {
        return id;
    }
}
