package http.common;

import http.exception.SessionExpireException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String sessionId;
    private final LocalDateTime expireTime;
    private final Map<String, Object> sessionValues;

    public HttpSession(final UUID uuid, LocalDateTime expireTime) {
        this.sessionId = uuid.toString();
        this.expireTime = expireTime;
        this.sessionValues = new HashMap<>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public Object getAttribute(final String name) {
        validSessionExpired();
        return sessionValues.get(name);
    }

    public void setAttribute(final String name, final Object value) {
        validSessionExpired();
        sessionValues.put(name, value);
    }

    private void validSessionExpired() {
        if (expireTime.isBefore(LocalDateTime.now())) {
            throw new SessionExpireException();
        }
    }

    public void removeAttribute(final String name) {
        sessionValues.remove(name);
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
