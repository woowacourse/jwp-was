package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionStore {
    private static Map<String, HttpSession> sessions;

    static {
        sessions = new HashMap<>();
    }

    public static HttpSession getSession(String key) {
        return sessions.containsKey(key) ? sessions.get(key) : createSession();
    }

    private static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new HttpSession(sessionId));
        return sessions.get(sessionId);
    }
}
