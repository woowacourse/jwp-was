package webserver;

import webserver.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private Map<UUID, HttpSession> sessions;

    private SessionManager() {
        this.sessions = new HashMap<>();
    }

    public static SessionManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String createSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid);
        sessions.put(uuid, httpSession);
        return uuid.toString();
    }

    public HttpSession getSession(String uuid) {
        return sessions.get(UUID.fromString(uuid));
    }

    private static final class LazyHolder {
        private static final SessionManager INSTANCE = new SessionManager();
    }
}
