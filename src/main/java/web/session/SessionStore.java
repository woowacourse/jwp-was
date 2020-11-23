package web.session;

import java.util.HashMap;
import java.util.Map;

public class SessionStore {
    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public static void addSession(HttpSession session) {
        addSession(session.getId(), session);
    }

    public static void addSession(String id, HttpSession session) {
        sessions.put(id, session);
    }

    public static boolean isContains(String id) {
        return sessions.containsKey(id);
    }

    public static void remove(String id) {
        sessions.remove(id);
    }
}
