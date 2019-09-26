package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private Map<String, Object> session;

    public HttpSession() {
        this.session = new HashMap<>();
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
