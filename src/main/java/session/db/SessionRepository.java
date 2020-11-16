package session.db;

import java.util.Map;

import com.google.common.collect.Maps;

import session.model.HttpSession;

public class SessionRepository {
    private static final Map<String, HttpSession> sessions = Maps.newHashMap();

    public static void addSession(String sessionId, HttpSession httpSession) {
        sessions.put(sessionId, httpSession);
    }

    public static HttpSession findById(String id) {
        return sessions.get(id);
    }

    public static void update(HttpSession httpSession) {
        sessions.replace(httpSession.getId(), httpSession);
    }
}
