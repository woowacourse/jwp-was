package http.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionRepository {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private SessionRepository() {
    }

    private static class SessionRepositoryLazyHolder {
        private static final SessionRepository INSTANCE = new SessionRepository();
    }

    public static SessionRepository getInstance() {
        return SessionRepositoryLazyHolder.INSTANCE;
    }

    public Session getSession(String sessionId) {
        return sessions.containsKey(sessionId) ? sessions.get(sessionId) : createSession();
    }

    public Session createSession() {
        String uuid = UUID.randomUUID().toString();
        while (sessions.get(uuid) != null) {
            uuid = UUID.randomUUID().toString();
        }

        Session session = new Session(uuid);
        sessions.put(session.getSessionId(), session);

        return session;
    }
}
