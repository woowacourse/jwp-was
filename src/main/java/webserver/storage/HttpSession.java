package webserver.storage;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    public static final String JSESSIONID = "JSESSIONID";

    private Map<String, Object> session;
    private String sessionId;

    public HttpSession(String sessionId) {
        this.session = new HashMap<>();
        this.sessionId = sessionId;
    }

    public void setAttribute(String name, Object value) {
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

    public String getId() {
        return sessionId;
    }

    public boolean isSameId(String sessionId) {
        return this.sessionId.equals(sessionId);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "session=" + session +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
