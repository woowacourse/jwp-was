package webserver.http.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionStorage {
    private static final ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();

    public static void add(Session session) {
        sessions.put(session.getId(), session);
    }

    public static Session get(String id) {
        return sessions.get(id);
    }

    public static boolean has(String id) {
        return sessions.containsKey(id);
    }
}
