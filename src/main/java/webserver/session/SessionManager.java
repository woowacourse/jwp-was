package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, Session> sessionManager = new HashMap<>();

    public static Session getSession() {
        final Session session = new Session();
        sessionManager.put(session.getId(), session);
        return session;
    }

    public static Session getSession(String uuid) {
        return sessionManager.get(uuid);
    }
}
