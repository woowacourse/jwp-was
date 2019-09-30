package http;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {
    private static ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    public static Session getSession(String sessionId) {
        sessionId = makeSession(sessionId);
        return sessions.get(sessionId);
    }

    private static String makeSession(String sessionId) {
        if (isNotContainSession(sessionId) || sessions.get(sessionId).isInvalid()) {
            sessionId = UUID.randomUUID().toString();
            Session session = new Session(sessionId);
            sessions.put(sessionId, session);
        }
        return sessionId;
    }

    private static boolean isNotContainSession(String sessionId) {
        return Objects.isNull(sessionId) || !sessions.containsKey(sessionId);
    }
}
