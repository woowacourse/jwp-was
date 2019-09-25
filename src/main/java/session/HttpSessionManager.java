package session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {

    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }

    public static void removeSession(String id) {
        sessions.remove(id);
    }

    public static HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public static void invalidate(String id) {
        HttpSession httpSession = sessions.get(id);
        httpSession.invalidate();
    }

    public static boolean isMappingValidSession(String id) {
        HttpSession httpSession = sessions.get(id);
        return httpSession != null && httpSession.isValid();
    }

    public static HttpSession createSession() {
        HttpSession httpSession = HttpSession.create(new RandomSessionUtils());
        HttpSessionManager.addSession(httpSession);
        return httpSession;
    }
}
