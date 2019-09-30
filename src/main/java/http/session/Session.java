package http.session;

import com.google.common.collect.Maps;

import java.util.Map;

public class Session {
    private final String sessionId;
    private Map<String, Object> sessionData = Maps.newHashMap();

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setAttribute(String key, Object value) {
        this.sessionData.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.sessionData.get(key);
    }

    public void removeAttribute(String key) {
        this.sessionData.remove(key);
    }

    public void invalidate() {
        sessionData.clear();
    }
}
