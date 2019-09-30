package webserver.session;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static Session issue() {
        final String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        final Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    public static Session retrieve(String sessionId) {
        return Optional.ofNullable(sessionId).map(sessions::get).orElse(null);
    }

    public static Session destroy(String sessionId) {
        return Optional.ofNullable(sessionId).map(sessions::remove).orElse(null);
    }
}