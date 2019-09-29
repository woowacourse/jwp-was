package http.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private static final Logger logger = LoggerFactory.getLogger(HttpSession.class);
    private final String sessionId;
    private final Map<String, Object> sessionValues;

    public HttpSession(final UUID uuid) {
        this.sessionId = uuid.toString();
        this.sessionValues = new HashMap<>();
        logger.info("session create {}", this);
    }

    public String getSessionId() {
        return sessionId;
    }

    public Object getAttribute(final String name) {
        return sessionValues.get(name);
    }

    public void setAttribute(final String name, final Object value) {
        sessionValues.put(name, value);
    }

    public void removeAttribute(final String name) {
        if (sessionValues.containsKey(name)) {
            sessionValues.remove(name);
        }
    }

    public void invalidate() {
        sessionValues.clear();
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "sessionId='" + sessionId + '\'' +
                ", sessionValues=" + sessionValues +
                '}';
    }
}
