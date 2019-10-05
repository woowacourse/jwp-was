package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public HttpSessionManager() { }

    public static HttpSession createSession() {
        String id = UUID.randomUUID().toString();
        HttpSession session = new HttpSession(id);
        sessions.put(id, session);
        return session;
    }

    public static HttpSession getSession(String id) {
        return sessions.getOrDefault(id, createSession());
    }

    public static boolean existSession(String id) {
        return sessions.get(id) != null;
    }
}
