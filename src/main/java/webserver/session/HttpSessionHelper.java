package webserver.session;

import java.util.UUID;

public class HttpSessionHelper {
    private static HttpSessions sessions = HttpSessions.getInstance();

    private HttpSessionHelper() {
    }

    public static HttpSession create(String key) {
        HttpSession session = new HttpSession(createSessionId());
        sessions.put(key, session);
        return session;
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
