package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static Map<String, HttpSession> sessions = new HashMap<>();

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
