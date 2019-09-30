package webserver.session;

import java.util.UUID;

public class HttpSessionHelper {
    private static HttpSessions sessions = new HttpSessions();

    private HttpSessionHelper() {
    }

    public static String create(HttpSession session) {
        String sessionId = createSessionId();
        sessions.put(sessionId, session);
        return sessionId;
    }

    public static HttpSession get(String key) {
        return sessions.get(key);
    }

    private static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public static void clear() {
        sessions.clear();
    }
}
