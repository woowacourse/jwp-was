package web.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionStorage {
    private static ConcurrentMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static HttpSession getSession(String id) {
        HttpSession session = sessions.get(id);

        if (session == null) {
            session = new HttpSession(id);
            sessions.put(id, session);
            return session;
        }

        return session;
    }

    public static void remove(String id) {
        sessions.remove(id);
    }
}
