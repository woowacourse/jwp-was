package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStorage {

    private static final Map<String, Session> sessions = new HashMap<>();

    public static boolean isSessionExist(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public static Session findSession(String sessionId) {
        validateSessionId(sessionId);
        return sessions.get(sessionId);
    }

    public static Session createSession() {
        Session session = new Session(UUID.randomUUID().toString());
        sessions.put(session.getId(), session);
        return session;
    }

    public static void removeSessionIfEmpty(String sessionId) {
        validateSessionId(sessionId);
        Session session = sessions.get(sessionId);

        if (session.isEmpty()) {
            sessions.remove(sessionId);
        }
    }

    private static void validateSessionId(String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            throw new IllegalArgumentException(
                "session that id is \"" + sessionId + "\" does not exist.");
        }
    }
}
