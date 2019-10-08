package webserver.env;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SessionStorage {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    protected SessionStorage() {}

    public Session issue() {
        final String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        final Session session = new Session(sessionId);
        this.sessions.put(sessionId, session);
        return session;
    }

    public Session retrieve(String sessionId) {
        return Optional.ofNullable(sessionId).map(this.sessions::get).orElse(null);
    }

    public Session destroy(String sessionId) {
        return Optional.ofNullable(sessionId).map(this.sessions::remove).orElse(null);
    }
}