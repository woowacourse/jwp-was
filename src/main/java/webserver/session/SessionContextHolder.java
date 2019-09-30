package webserver.session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionContextHolder {
    private final static Map<String, Optional<HttpSession>> sessions = new ConcurrentHashMap<>();

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), Optional.of(session));
    }

    public static Optional<HttpSession> findSessionById(String sessionId) {
        return sessions.getOrDefault(sessionId, Optional.empty());
    }
}
