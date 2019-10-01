package http.session;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class SessionRepository {
    private final Map<String, Session> sessions;

    private SessionRepository() {
        sessions = Collections.synchronizedMap(Maps.newHashMap());
    }

    public static SessionRepository getInstance() {
        return SessionRepositoryLazyHolder.INSTANCE;
    }

    public Session getSession(String sessionId) {
        return sessions.getOrDefault(sessionId, createSession());
    }

    private Session createSession() {
        String sessionId = createUniqueSessionId();
        Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    private String createUniqueSessionId() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        } while (sessions.containsKey(uuid));

        return uuid;
    }

    private static class SessionRepositoryLazyHolder {
        private static final SessionRepository INSTANCE = new SessionRepository();
    }
}
