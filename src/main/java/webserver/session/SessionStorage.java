package webserver.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static Map<String, HttpSession> sessions;

    static {
        sessions = new ConcurrentHashMap<>();
    }

    public static HttpSession create(IdGenerationStrategy idGenerator) {
        String id = idGenerator.generate();
        HttpSession session = new HttpSession(id);
        sessions.put(id, session);
        return session;
    }

    public static HttpSession get(String id) {
        return sessions.get(id);
    }

    public static boolean exists(String id) {
        return sessions.containsKey(id);
    }
}
