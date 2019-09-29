package http.session;

import http.session.sessionkeygenerator.SessionKeyGenerator;

import java.util.Map;
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

    public Session getSession(String sessionId, SessionKeyGenerator sessionKeyGenerator) {
        try {
            return sessions.containsKey(sessionId) ? sessions.get(sessionId) : createSession(sessionKeyGenerator);

        } catch (NullPointerException e) {
            return createSession(sessionKeyGenerator);
        }
    }

    public Session createSession(SessionKeyGenerator sessionKeyGenerator) {
        String uuid = sessionKeyGenerator.createSessionKey(sessions);

        Session session = new Session(uuid);
        sessions.put(session.getSessionId(), session);

        return session;
    }
}
