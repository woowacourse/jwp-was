package webserver.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static Map<String, HttpSession> sessions;

    static {
        sessions = new ConcurrentHashMap<>();
    }

    public static HttpSession create(IdGenerationStrategy generator) {
        String randomId = generator.generate();
        HttpSession session = new HttpSession(randomId);
        sessions.put(randomId, session);
        return session;
    }

    public static HttpSession get(String id) {
        return sessions.get(id);
    }

    public static boolean exists(String id) {
        return sessions.containsKey(id);
    }
}
