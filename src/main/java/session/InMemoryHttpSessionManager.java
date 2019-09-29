package session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class InMemoryHttpSessionManager implements HttpSessionManager {
    private static Map<String, HttpSession> sessions;
    private static InMemoryHttpSessionManager instance;

    private InMemoryHttpSessionManager() {
        sessions = Maps.newHashMap();
    }

    public static InMemoryHttpSessionManager getInstance() {
        if (instance == null) {
            instance = new InMemoryHttpSessionManager();
        }
        return instance;
    }

    @Override
    public HttpSession findSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public HttpSession createSession() {
        HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }
}


