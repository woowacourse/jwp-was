package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStore {
    private static Map<String, Session> sessions = new HashMap<>();

    public static Session getSession(String sessionId) {
        sessionId = makeSession(sessionId);
        return sessions.get(sessionId);
    }

    private static String makeSession(String sessionId) {
        if (isNotContainSession(sessionId) || sessions.get(sessionId).isInvalidTrue()) {
            sessionId = UUID.randomUUID().toString();
            Session session = new Session(sessionId);
            sessions.put(sessionId, session);
        }
        return sessionId;
    }

    private static boolean isNotContainSession(String sessionId) {
        return !sessions.containsKey(sessionId);
    }
}
