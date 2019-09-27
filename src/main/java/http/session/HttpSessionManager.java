package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HttpSessionManager {
    private Map<String, HttpSession> sessions = new HashMap<>();

    private HttpSessionManager() {

    }

    public static HttpSessionManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final HttpSessionManager INSTANCE = new HttpSessionManager();
    }

    public HttpSession getSession(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId))
                .orElseGet(this::createSession);
    }

    public HttpSession createSession() {
        UUID uuid = UUID.randomUUID();
        HttpSession httpSession = new HttpSession(uuid.toString());
        sessions.put(uuid.toString(), httpSession);
        return httpSession;
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
