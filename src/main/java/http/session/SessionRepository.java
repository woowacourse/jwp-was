package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionRepository {
    private final Map<String, Session> sessions = new HashMap<>();

    private SessionRepository() {
    }

    private static class SessionRepositoryLazyHolder {
        private static final SessionRepository INSTANCE = new SessionRepository();
    }

    public static SessionRepository getInstance() {
        return SessionRepositoryLazyHolder.INSTANCE;
    }

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public Session createSession() {
        String uuid = UUID.randomUUID().toString();
        while (sessions.get(uuid) != null) {
            uuid = UUID.randomUUID().toString();
        }
        return new Session(uuid);
    }
}
