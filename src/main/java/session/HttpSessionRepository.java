package session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionRepository {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static void setSession(String key, HttpSession httpSession) {
        sessions.put(key, httpSession);
    }

    public static HttpSession getSession(String key) {
        return sessions.get(key);
    }
}
