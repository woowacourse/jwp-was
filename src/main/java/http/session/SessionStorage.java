package http.session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class SessionStorage {
    public static final String JSESSIONID = "JSESSIONID";

    private final Map<String, Session> sessions = Maps.newHashMap();

    private SessionStorage() {
    }

    public static SessionStorage getInstance() {
        return SessionsLazyHolder.INSTANCE;
    }

    public Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public Session createSession() {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    private static class SessionsLazyHolder {
        private static final SessionStorage INSTANCE = new SessionStorage();
    }
}
