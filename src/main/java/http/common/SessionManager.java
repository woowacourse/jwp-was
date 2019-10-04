package http.common;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId);
        sessions.put(sessionId, httpSession);
        return httpSession;
    }

    public static HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
