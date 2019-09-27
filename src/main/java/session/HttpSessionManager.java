package session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    private static Map<String, HttpSession> sessions;
    private static HttpSessionManager instance;

    private HttpSessionManager() {
        sessions = Maps.newHashMap();
    }

    public static HttpSessionManager getInstance() {
        if (instance == null) {
            instance = new HttpSessionManager();
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
