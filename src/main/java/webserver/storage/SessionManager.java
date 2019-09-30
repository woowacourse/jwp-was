package webserver.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private SessionManager() {
    }

    private static class SessionManagerHolder {
        private static final SessionManager INSTANCE = new SessionManager();
    }

    public static SessionManager getInstance() {
        return SessionManagerHolder.INSTANCE;
    }

    private static Map<String, HttpSession> sessionManager = new HashMap<>();

    public HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId);
        sessionManager.put(sessionId, httpSession);
        return httpSession;
    }

    public HttpSession createSession(String key) {
        HttpSession httpSession = new HttpSession(key);
        sessionManager.put(key, httpSession);
        return httpSession;
    }

    public boolean containsKey(String key) {
        return sessionManager.containsKey(key);
    }

    public HttpSession getSession(String sessionId) {
        return sessionManager.get(sessionId);
    }
}
