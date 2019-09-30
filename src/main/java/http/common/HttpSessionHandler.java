package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionHandler {
    private static final Map<String, HttpSession> sessionsContext;

    static {
        sessionsContext = new HashMap<>();
    }

    public static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();

        sessionsContext.put(sessionId, new HttpSession(sessionId));

        return sessionsContext.get(sessionId);
    }

    public static HttpSession getSession(String sessionId) {
        return sessionsContext.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessionsContext.remove(sessionId);
    }
}
