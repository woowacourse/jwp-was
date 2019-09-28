package webserver.session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class SessionContextHolder {
    private static Map<String, Optional<HttpSession>> sessions = Maps.newHashMap();

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), Optional.of(session));
    }

    public static Optional<HttpSession> findSessionById(String sessionId) {
        return sessions.getOrDefault(sessionId, Optional.empty());
    }
}
