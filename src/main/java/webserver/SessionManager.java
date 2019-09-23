package webserver;

import java.util.HashMap;
import java.util.Map;

class SessionManager {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }

    public static HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public static void removeSession(HttpSession session) {
        sessions.remove(session.getId());
    }
}
