package kr.wootecat.dongle.model.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession implements Session {

    private final String id;
    private final Map<String, Object> session;

    private HttpSession(String id, Map<String, Object> session) {
        this.id = id;
        this.session = session;
    }

    public static HttpSession ofEmpty(String sessionId) {
        return new HttpSession(sessionId, new HashMap<>());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return session.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        session.remove(name);
    }

    @Override
    public void invalidate() {
        session.clear();
    }
}
