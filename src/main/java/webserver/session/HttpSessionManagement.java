package webserver.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManagement {
    private static final Map<String, HttpSession> sessions;

    static {
        sessions = new ConcurrentHashMap<>();
    }

    public static HttpSession create() {
        String uuid = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(uuid);
        sessions.put(uuid, httpSession);
        return httpSession;
    }

    public static void put(String key, HttpSession httpSession) {
        sessions.put(key, httpSession);
    }

    public static HttpSession get(String key) {
        return sessions.get(key);
    }
}
