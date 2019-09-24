package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class SessionManager {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getSession(String id) {
        HttpSession session = sessions.get(id);

        if (shouldCreateNewSession(id, session))  {
            removeSession(session);
            session = createSession();
            addSession(session);
        }

        return session;
    }

    private static boolean shouldCreateNewSession(String id, HttpSession session) {
        return id == null || session == null || session.isInvalid();
    }

    private static void removeSession(HttpSession session) {
        if (session != null) {
            sessions.remove(session.getId());
        }
    }

    public static HttpSession createSession() {
        return new HttpSession(UUID.randomUUID().toString(), new HashMap<>());
    }

    private static void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }
}
