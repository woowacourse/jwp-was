package http.supoort;

import http.model.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static Map<String, HttpSession> sessionManager = new HashMap<>();

    private SessionManager() {
    }

    public static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId);
        sessionManager.put(sessionId, httpSession);
        return httpSession;
    }

    public static HttpSession getSession(String sessionId) {
        return sessionManager.get(sessionId);
    }
}