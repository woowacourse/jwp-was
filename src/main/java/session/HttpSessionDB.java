package session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class HttpSessionDB {
    private static Map<String, HttpSession> sessions;
    private static HttpSessionDB instance;

    private HttpSessionDB() {
        sessions = Maps.newHashMap();
    }

    public static HttpSessionDB getInstance() {
        if (instance == null) {
            instance = new HttpSessionDB();
        }
        return instance;
    }

    public HttpSession createSession() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public HttpSession findOrCreateSession(String id) {
        return sessions.getOrDefault(id, createSession());
    }
}
