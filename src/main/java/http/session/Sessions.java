package http.session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Sessions {
    private final Map<String, Session> sessions = Maps.newHashMap();

    private Sessions() {
    }

    public static Sessions getInstance() {
        return SessionsLazyHolder.INSTANCE;
    }

    public Session getSession(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId)).orElseGet(this::createSession);
    }

    private Session createSession() {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    private static class SessionsLazyHolder {
        private static final Sessions INSTANCE = new Sessions();
    }
}
