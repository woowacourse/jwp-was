package webserver.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    public Session issue() {
        final String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        final Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    public Session retrieve(String sessionId) {
        return sessions.get(sessionId);
    }

    public Session destroy(String sessionId) {
        return sessions.remove(sessionId);
    }
}