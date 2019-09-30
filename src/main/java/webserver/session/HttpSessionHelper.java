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

    private static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public static HttpSession get(String key) {
        return sessions.get(key);
    }

    public static void clear() {
        sessions.clear();
    }

    public static boolean isValid(String key) {
        HttpSession session = get(key);
        return key != null && session != null ;
    }
}
