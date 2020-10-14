package http.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionContainer {
    public static final String SESSION_KEY_FOR_COOKIE = "session_id";

    private final Map<String, HttpSession> sessionContainer = new HashMap<>();

    private SessionContainer() {
    }

    public static SessionContainer getInstance() {
        return Holder.INSTANCE;
    }

    public HttpSession getSession(String sessionId) {
        return sessionContainer.get(sessionId);
    }

    public void put(String sessionId, HttpSession httpSession) {
        sessionContainer.put(sessionId, httpSession);
    }

    public String createSession() {
        String sessionId = String.valueOf(UUID.randomUUID());
        sessionContainer.put(sessionId, new HttpSession(sessionId));
        return sessionId;
    }

    private static class Holder {
        private static final SessionContainer INSTANCE = new SessionContainer();
    }
}
